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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
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

    @Autowired
    public AuthService(UserRepository usersRepository, UserRepository userRepository, AccountUtils accountUtils, RoleRepository roleRepository, JavaMailSender mailSender) {
        this.usersRepository = usersRepository;
        this.userRepository = userRepository;
        this.accountUtils = accountUtils;
        this.roleRepository = roleRepository;
        this.mailSender = mailSender;
    }


    public void register(RegisterCustomerRequest registerCustomerRequest) throws MessagingException {
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
                .role(roleRepository.findByName("CUSTOMER"))
                .createAt(LocalDateTime.now())
                .customerStatus(CustomerStatusEnum.UNVERIFIED)
                .build();
        userRepository.save(newCustomer);
        sendVerificationEmail(registerCustomerRequest.getEmail());
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
                .id(user.getId())
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
                "<body style='font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;'>" +
                "<table align='center' width='600' style='border-collapse: collapse; background-color: #ffffff; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
                "    <tr>" +
                "        <td style='padding: 20px; text-align: center; background-color: #4CAF50; color: #ffffff;'>" +
                "            <h2 style='margin: 0;'>Xác thực tài khoản của bạn</h2>" +
                "        </td>" +
                "    </tr>" +
                "    <tr>" +
                "        <td style='padding: 20px; font-size: 16px; color: #333333;'>" +
                "            <p>Chào <strong>" + users.getName() + "</strong>,</p>" +
                "            <p>Cảm ơn bạn đã đăng ký tài khoản với chúng tôi! Để hoàn tất quá trình đăng ký, vui lòng nhấn vào nút bên dưới để xác thực tài khoản của bạn:</p>" +
                "            <p style='text-align: center; margin: 20px 0;'>" +
                "                <a href='" + url + "' style='background-color: #4CAF50; color: white; padding: 15px 25px; text-decoration: none; font-size: 16px; border-radius: 5px; display: inline-block;'>Xác thực tài khoản</a>" +
                "            </p>" +
                "            <p>Nếu bạn không đăng ký tài khoản này, vui lòng bỏ qua email này.</p>" +
                "        </td>" +
                "    </tr>" +
                "    <tr>" +
                "        <td style='padding: 20px; text-align: center; font-size: 14px; color: #555555; background-color: #f4f4f4;'>" +
                "            <p style='margin: 0;'>Trân trọng,<br><strong>Đội ngũ hỗ trợ</strong></p>" +
                "        </td>" +
                "    </tr>" +
                "</table>" +
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

    public void sendResetPasswordEmail(String email) throws MessagingException {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String token = generateToken(users);
        String url = "http://localhost:8080/auth/reset-password?token=" + token;
        String subject = "Yêu cầu reset mật khẩu";

        String message = "<html>" +
                "<body style='font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;'>" +
                "<table align='center' width='600' style='border-collapse: collapse; background-color: #ffffff; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
                "    <tr>" +
                "        <td style='padding: 20px; text-align: center; background-color: #E74C3C; color: #ffffff;'>" +
                "            <h2 style='margin: 0;'>Yêu cầu đặt lại mật khẩu</h2>" +
                "        </td>" +
                "    </tr>" +
                "    <tr>" +
                "        <td style='padding: 20px; font-size: 16px; color: #333333;'>" +
                "            <p>Chào <strong>" + users.getName() + "</strong>,</p>" +
                "            <p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>" +
                "            <p>Để đặt lại mật khẩu, vui lòng nhấn vào nút bên dưới:</p>" +
                "            <p style='text-align: center; margin: 20px 0;'>" +
                "                <a href='" + url + "' style='background-color: #E74C3C; color: white; padding: 15px 25px; text-decoration: none; font-size: 16px; border-radius: 5px; display: inline-block;'>Đặt lại mật khẩu</a>" +
                "            </p>" +
                "            <p style='color: #555555;'>Nếu bạn không yêu cầu đặt lại mật khẩu, bạn có thể bỏ qua email này.</p>" +
                "        </td>" +
                "    </tr>" +
                "    <tr>" +
                "        <td style='padding: 20px; text-align: center; font-size: 14px; color: #555555; background-color: #f4f4f4;'>" +
                "            <p style='margin: 0;'>Trân trọng,<br><strong>Đội ngũ hỗ trợ</strong></p>" +
                "        </td>" +
                "    </tr>" +
                "</table>" +
                "</body>" +
                "</html>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(message, true);

        mailSender.send(mimeMessage);
    }

    public void resetPassword(String token, String newPassword, String confirmPassword) throws MessagingException {
        Users users = validateToken(token);
        if (!newPassword.equals(confirmPassword)) {

            throw new AppException(ErrorCode.INVALID_REPEAT_PASSWORD);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        users.setPassword(encodedPassword);
        users.setUpdateAt(LocalDateTime.now());
        users.setUpdateBy(users.getId());
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
