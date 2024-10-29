package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.AuthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.ChangePasswordRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.auth.RegisterCustomerRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.AuthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.CustomerStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.RoleRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;


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

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;


    public AuthResponse register(RegisterCustomerRequest registerCustomerRequest) {
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
        var token = generateToken(newCustomer);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse login(AuthRequest authRequest) {
        var user = usersRepository.findByEmail(authRequest.getEmail()).
                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
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

    public String generateToken(Users user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issuer("KoiDeliveryOrderingSystem")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
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

}
