package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.CustomerStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.customer.UpdateProfileRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.UserResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountUtils accountUtils;

    public UserResponse getCustomerProfile() {
        Users users = accountUtils.getCurrentUser();
        if (users == null){
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        return convertToUserResponse(users);
    }

    public UserResponse updateProfile(UpdateProfileRequest request){
        Users users = accountUtils.getCurrentUser();
        if (users == null){
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
}
