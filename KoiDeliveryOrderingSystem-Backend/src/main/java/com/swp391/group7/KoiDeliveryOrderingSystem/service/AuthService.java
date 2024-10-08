package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.AuthRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.ChangePasswordRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.RegisterCustomerRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.AuthResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Customers;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.CustomerStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CustomersRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UsersRepository;
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
    private UsersRepository usersRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private AccountUtils accountUtils;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;

    public AuthResponse registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        var customer = customersRepository.findByEmail(registerCustomerRequest.getEmail());
        if (customer.isPresent()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(registerCustomerRequest.getPassword());
        new Customers();
        Customers newCustomer = Customers.builder()
                .email(registerCustomerRequest.getEmail())
                .password(encodedPassword)
                .name(registerCustomerRequest.getName())
                .createAt(LocalDateTime.now())
                .customerStatus(CustomerStatusEnum.UNVERIFIED)
                .build();
        customersRepository.save(newCustomer);
        var token = generateCustomerToken(newCustomer);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse authenticateUser(AuthRequest authRequest) {
        var user = usersRepository.findByEmail(authRequest.getEmail()).
                orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateUserToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse authenticateCustomer(AuthRequest authRequest) {
        var customer = customersRepository.findByEmail(authRequest.getEmail()).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authRequest.getPassword(), customer.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateCustomerToken(customer);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null){
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if(!Objects.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmPassword())){
            throw new AppException(ErrorCode.INVALID_REPEAT_PASSWORD);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(changePasswordRequest.getOldPassword(), users.getPassword());
        if (authenticated) {
            users.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            usersRepository.save(users);
        }else{
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return "Password changed successfully";
    }

    public String generateUserToken(Users user) {
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

    public String generateCustomerToken(Customers customers) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(customers.getId().toString())
                .issuer("KoiDeliveryOrderingSystem")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
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
