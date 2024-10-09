package com.swp391.group7.KoiDeliveryOrderingSystem.config;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.DeliveryMethod;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.UserStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishCategory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Role;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.DeliveryMethodRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishCategoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.RoleRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Transactional
public class DataLoader {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    @Bean
    CommandLineRunner initData(UsersRepository usersRepository, RoleRepository roleRepository, DeliveryMethodRepository deliveryMethodRepository, FishCategoryRepository fishCategoryRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role adminRole = Role.builder()
                        .id(1)
                        .name("ADMIN")
                        .build();
                Role staffRole = Role.builder()
                        .id(2)
                        .name("STAFF")
                        .build();
                Role managerRole = Role.builder()
                        .id(3)
                        .name("MANAGER")
                        .build();
                roleRepository.save(adminRole);
                roleRepository.save(staffRole);
                roleRepository.save(managerRole);
            }
            if(usersRepository.count() == 0){
                Users admin1 = Users.builder()
                        .role(roleRepository.findByName("ADMIN"))
                        .email("admin1@admin.com")
                        .name("admin1")
                        .password(passwordEncoder.encode("12345"))
                        .createAt(LocalDateTime.now())
                        .userStatus(UserStatusEnum.AVAILABLE)
                        .build();
                usersRepository.save(admin1);
            }
            if(deliveryMethodRepository.count() == 0){
                DeliveryMethod van = DeliveryMethod.builder()
                        .deliveryName("VAN")
                        .build();
                DeliveryMethod plane = DeliveryMethod.builder()
                        .deliveryName("PLANE")
                        .build();
                DeliveryMethod boat = DeliveryMethod.builder()
                        .deliveryName("BOAT")
                        .build();
                DeliveryMethod train = DeliveryMethod.builder()
                        .deliveryName("TRAIN")
                        .build();
                deliveryMethodRepository.save(van);
                deliveryMethodRepository.save(plane);
                deliveryMethodRepository.save(boat);
                deliveryMethodRepository.save(train);
            }
            if(fishCategoryRepository.count() == 0){
                FishCategory fishA = FishCategory.builder()
                        .fishCategoryName("Type A")
                        .fishCategoryDescription("Super A")
                        .build();
                FishCategory fishB = FishCategory.builder()
                        .fishCategoryName("Type B")
                        .fishCategoryDescription("Super B")
                        .build();
                FishCategory fishC = FishCategory.builder()
                        .fishCategoryName("Type C")
                        .fishCategoryDescription("Super C")
                        .build();
                fishCategoryRepository.save(fishA);
                fishCategoryRepository.save(fishB);
                fishCategoryRepository.save(fishC);
            }
        };
    }
}
