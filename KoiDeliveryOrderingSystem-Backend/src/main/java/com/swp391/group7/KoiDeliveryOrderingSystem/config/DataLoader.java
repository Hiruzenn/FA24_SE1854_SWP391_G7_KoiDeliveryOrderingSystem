package com.swp391.group7.KoiDeliveryOrderingSystem.config;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.CustomerStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.*;
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
    String encodedPassword = passwordEncoder.encode("123456");

    @Bean
    CommandLineRunner initData(UserRepository userRepository, RoleRepository roleRepository,
                               DeliveryMethodRepository deliveryMethodRepository, FishCategoryRepository fishCategoryRepository,
                               HealthServiceCategoryRepository healthServiceCategoryRepository
    ) {
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
            if (userRepository.count() == 0) {

                Users user = Users.builder()
                        .name("User")
                        .email("user@gmail.com")
                        .password(encodedPassword)
                        .role(roleRepository.findByName("CUSTOMERS"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users staff = Users.builder()
                        .name("Staff")
                        .email("staff@gmail.com")
                        .password(encodedPassword)
                        .role(roleRepository.findByName("STAFF"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users manager = Users.builder()
                        .name("Manager")
                        .email("manager@gmail.com")
                        .password(encodedPassword)
                        .role(roleRepository.findByName("MANAGER"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users admin = Users.builder()
                        .name("Admin")
                        .email("admin@gmail.com")
                        .password(encodedPassword)
                        .role(roleRepository.findByName("ADMIN"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                userRepository.save(user);
                userRepository.save(staff);
                userRepository.save(manager);
                userRepository.save(admin);
            }
            if (deliveryMethodRepository.count() == 0) {
                DeliveryMethod van = DeliveryMethod.builder()
                        .deliveryMethodName("VAN")
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                DeliveryMethod plane = DeliveryMethod.builder()
                        .deliveryMethodName("PLANE")
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                DeliveryMethod boat = DeliveryMethod.builder()
                        .deliveryMethodName("BOAT")
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                DeliveryMethod train = DeliveryMethod.builder()
                        .deliveryMethodName("TRAIN")
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                deliveryMethodRepository.save(van);
                deliveryMethodRepository.save(plane);
                deliveryMethodRepository.save(boat);
                deliveryMethodRepository.save(train);
            }
            if (fishCategoryRepository.count() == 0) {
                FishCategory fishA = FishCategory.builder()
                        .fishCategoryName("Type A")
                        .fishCategoryDescription("Super A")
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                FishCategory fishB = FishCategory.builder()
                        .fishCategoryName("Type B")
                        .fishCategoryDescription("Super B")
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                FishCategory fishC = FishCategory.builder()
                        .fishCategoryName("Type C")
                        .fishCategoryDescription("Super C")
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                fishCategoryRepository.save(fishA);
                fishCategoryRepository.save(fishB);
                fishCategoryRepository.save(fishC);
            }
            if (healthServiceCategoryRepository.count() == 0) {
                HealthServiceCategory healthServiceCategoryA = HealthServiceCategory.builder()
                        .serviceName("Prepare oxygen bag and pump")
                        .serviceDescription("Ensure adequate oxygen and space for the fish.")
                        .price(Float.parseFloat(String.valueOf(500.0)))
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                HealthServiceCategory healthServiceCategoryB = HealthServiceCategory.builder()
                        .serviceName("Add salt or anti-stress medication")
                        .serviceDescription("Reduce stress and risk of infection.")
                        .price(Float.parseFloat(String.valueOf(400.0)))
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                HealthServiceCategory healthServiceCategoryC = HealthServiceCategory.builder()
                        .serviceName("Maintain proper temperature and lighting")
                        .serviceDescription("Stabilize the transport environment")
                        .price(Float.parseFloat(String.valueOf(500.0)))
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                HealthServiceCategory healthServiceCategoryD = HealthServiceCategory.builder()
                        .serviceName("Use insulated boxes")
                        .serviceDescription("Protect fish from thermal shock and vibration.")
                        .price(Float.parseFloat(String.valueOf(500.0)))
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                HealthServiceCategory healthServiceCategoryE = HealthServiceCategory.builder()
                        .serviceName("Tracking and post-shipment support")
                        .serviceDescription("Update information and instruct recipients on how to care for fish.")
                        .price(Float.parseFloat(String.valueOf(500.0)))
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                healthServiceCategoryRepository.save(healthServiceCategoryA);
                healthServiceCategoryRepository.save(healthServiceCategoryB);
                healthServiceCategoryRepository.save(healthServiceCategoryC);
                healthServiceCategoryRepository.save(healthServiceCategoryD);
                healthServiceCategoryRepository.save(healthServiceCategoryE);
            }
        };
    }
}
