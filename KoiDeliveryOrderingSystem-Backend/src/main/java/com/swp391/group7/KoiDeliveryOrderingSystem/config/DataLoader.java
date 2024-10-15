package com.swp391.group7.KoiDeliveryOrderingSystem.config;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.DeliveryMethod;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishCategory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Role;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.DeliveryMethodRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishCategoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.RoleRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DataLoader {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    @Bean
    CommandLineRunner initData(UserRepository userRepository, RoleRepository roleRepository, DeliveryMethodRepository deliveryMethodRepository, FishCategoryRepository fishCategoryRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role adminRole = Role.builder()
                        .name("ADMIN")
                        .build();
                Role staffRole = Role.builder()
                        .name("STAFF")
                        .build();
                Role managerRole = Role.builder()
                        .name("MANAGER")
                        .build();
                Role customersRole = Role.builder()
                        .name("CUSTOMERS")
                        .build();
                roleRepository.save(adminRole);
                roleRepository.save(customersRole);
                roleRepository.save(managerRole);
                roleRepository.save(staffRole);


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
