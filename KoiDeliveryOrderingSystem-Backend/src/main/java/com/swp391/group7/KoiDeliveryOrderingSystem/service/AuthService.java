package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.AuthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.CustomerStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.RoleRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountUtils accountUtils;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;


    public AuthResponse register(RegisterCustomerRequest registerCustomerRequest) throws MessagingException {
            var customer = userRepository.findByEmail(registerCustomerRequest.getEmail());
            if (customer.isPresent()) {
                throw new AppException(ErrorCode.ACCOUNT_REGISTERED);
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            String encodedPassword = passwordEncoder.encode(registerCustomerRequest.getPassword());
            new Users();
            Users newCustomer = Users.builder()
                    .email(registerCustomerRequest.getEmail())
                    .password(encodedPassword)
                    .name(registerCustomerRequest.getName())
                    .role(roleRepository.findByName("CUSTOMERS"))
                    .createAt(LocalDateTime.now())
                    .customerStatus(CustomerStatusEnum.UNVERIFIED)
                    .build();
            userRepository.save(newCustomer);
            sendVerificationEmail(registerCustomerRequest.getEmail());
            var token = generateToken(newCustomer);
            return AuthResponse.builder()
                    .token(token)
                    .build();
    }

    public AuthResponse login(AuthRequest authRequest) {
        var user = usersRepository.findByEmailAndCustomerStatus(authRequest.getEmail(), CustomerStatusEnum.VERIFIED).
                orElseThrow(() -> new AppException(ErrorCode.UNVERIFIED_ACCOUNT));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        var token = generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .role(user.getRole().getName())
                .build();
    }

    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.INVALID_REPEAT_PASSWORD);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(changePasswordRequest.getOldPassword(), users.getPassword());
        if (authenticated) {
            users.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            usersRepository.save(users);
        } else {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return "Password changed successfully";
    }

    public void sendVerificationEmail(String email) throws MessagingException {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String token = generateToken(users);
        String url = "http://localhost:8080/auth/verify?token=" + token;
        String subject = "Xác thực tài khoản của bạn";

        String message = "<html>" +
                "<body>" +
                "<h2>Xác thực tài khoản của bạn</h2>" +
                "<p>Chào " + users.getName() + ",</p>" +
                "<p>Cảm ơn bạn đã đăng ký tài khoản với chúng tôi! Để hoàn tất quá trình đăng ký, vui lòng nhấn vào liên kết bên dưới để xác thực tài khoản của bạn:</p>" +
                "<p><a href=\"" + url + "\" style=\"background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;\">Xác thực tài khoản</a></p>" +
                "<p>Trân trọng,<br>Đội ngũ hỗ trợ</p>" +
                "</body>" +
                "</html>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(message, true);

        mailSender.send(mimeMessage);
    }

    public void verifyAccount(String token) {
        Users users = validateToken(token);
        users.setCustomerStatus(CustomerStatusEnum.VERIFIED);
        usersRepository.save(users);
    }

    public String generateToken(Users user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issuer("KoiDeliveryOrder   ingSystem")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()))
                .claim("role", user.getRole().getName())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private Users validateToken(String token) {
        try {
            JWSObject jwt = JWSObject.parse(token);
            MACVerifier verifier = new MACVerifier(signerKey.getBytes());
            if (!jwt.verify(verifier)) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
            JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwt.getPayload().toJSONObject());

            String userId = claimsSet.getSubject();

            return userRepository.findById(Integer.valueOf(userId))
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }
}
