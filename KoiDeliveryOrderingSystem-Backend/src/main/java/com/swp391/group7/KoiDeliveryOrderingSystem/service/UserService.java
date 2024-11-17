package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.CustomerStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Role;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.user.CreateUserRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.user.UpdateProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.UserResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.RoleRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private RoleRepository roleRepository;

    public UserResponse getCustomerProfile() {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        return convertToUserResponse(users);
    }

    public UserResponse viewById(Integer userId) {
        Users users = userRepository.findByIdAndCustomerStatus(userId, CustomerStatusEnum.VERIFIED)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return convertToUserResponse(users);
    }

    public UserResponse updateProfile(UpdateProfileRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        users.setName(request.getName());
        users.setAddress(request.getAddress());
        users.setAvatar(request.getAvatar());
        users.setPhone(request.getPhone());
        users.setUpdateAt(LocalDateTime.now());
        users.setUpdateBy(users.getId());
        userRepository.save(users);
        return convertToUserResponse(users);
    }

    public List<UserResponse> getAllUsers() {
        List<Users> users = userRepository.findAll();
        return convertToListUserResponse(users);
    }

    public UserResponse createUser(CreateUserRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Users createUser = Users.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .customerStatus(CustomerStatusEnum.VERIFIED)
                .address(request.getAddress())
                .avatar(request.getAvatar())
                .phone(request.getPhone())
                .role(role)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .build();
        userRepository.save(createUser);
        return convertToUserResponse(createUser);
    }

    public UserResponse blockUnblockUser(Integer userId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Users usersManagement = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (usersManagement.getCustomerStatus() == CustomerStatusEnum.VERIFIED) {
            usersManagement.setCustomerStatus(CustomerStatusEnum.UNVERIFIED);
        } else if (usersManagement.getCustomerStatus() == CustomerStatusEnum.UNVERIFIED) {
            usersManagement.setCustomerStatus(CustomerStatusEnum.VERIFIED);
        }
        userRepository.save(usersManagement);
        return convertToUserResponse(usersManagement);
    }

    public List<UserResponse> viewDeliveryStaff() {
        Role role = roleRepository.findByName("DELIVERY_STAFF");
        List<Users> deliveryStaff = userRepository.findByRoleAndCustomerStatus(role, CustomerStatusEnum.VERIFIED);
        return convertToListUserResponse(deliveryStaff);
    }

    public UserResponse convertToUserResponse(Users users) {
        return UserResponse.builder()
                .id(users.getId())
                .name(users.getName())
                .email(users.getEmail())
                .address(users.getAddress())
                .avatar(users.getAvatar())
                .phone(users.getPhone())
                .status(users.getCustomerStatus())
                .build();
    }

    public List<UserResponse> convertToListUserResponse(List<Users> users) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (Users userList : users) {
            userResponses.add(convertToUserResponse(userList));
        }
        return userResponses;
    }
}
