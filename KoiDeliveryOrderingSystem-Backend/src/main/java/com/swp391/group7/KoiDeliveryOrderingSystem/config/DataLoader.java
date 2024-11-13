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
                Role customer = Role.builder()
                        .name("CUSTOMER")
                        .build();
                Role saleStaff = Role.builder()
                        .name("SALE_STAFF")
                        .build();
                Role deliveringStaff = Role.builder()
                        .name("DELIVERY_STAFF")
                        .build();
                Role manager = Role.builder()
                        .name("MANAGER")
                        .build();
                roleRepository.save(customer);
                roleRepository.save(manager);
                roleRepository.save(deliveringStaff);
                roleRepository.save(saleStaff);
            }
            if (userRepository.count() == 0) {

                Users user = Users.builder()
                        .name("Customer")
                        .email("Customer@gmail.com")
                        .password(encodedPassword)
                        .role(roleRepository.findByName("CUSTOMER"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users staff = Users.builder()
                        .name("SaleStaff")
                        .email("SaleStaff@gmail.com")
                        .password(encodedPassword)
                        .role(roleRepository.findByName("SALE_STAFF"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users manager = Users.builder()
                        .name("Manager")
                        .email("manager@gmail.com")
                        .password(encodedPassword)
                        .role(roleRepository.findByName("MANAGER"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users deliveryStaff = Users.builder()
                        .name("DeliveryStaff")
                        .email("DeliveryStaff@gmail.com")
                        .password(encodedPassword)
                        .role(roleRepository.findByName("DELIVERY_STAFF"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                userRepository.save(user);
                userRepository.save(staff);
                userRepository.save(manager);
                userRepository.save(deliveryStaff);
            }
            if (deliveryMethodRepository.count() == 0) {
                DeliveryMethod van = DeliveryMethod.builder()
                        .name("VAN")
                        .price(50f)
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                DeliveryMethod plane = DeliveryMethod.builder()
                        .name("PLANE")
                        .price(200f)
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                DeliveryMethod boat = DeliveryMethod.builder()
                        .name("BOAT")
                        .price(150f)
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                DeliveryMethod train = DeliveryMethod.builder()
                        .name("TRAIN")
                        .price(100f)
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                deliveryMethodRepository.save(van);
                deliveryMethodRepository.save(plane);
                deliveryMethodRepository.save(boat);
                deliveryMethodRepository.save(train);
            }
            if (fishCategoryRepository.count() == 0) {
                FishCategory fishA = FishCategory.builder()
                        .name("Type A")
                        .description("Super A")
                        .price(50000F)
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                FishCategory fishB = FishCategory.builder()
                        .name("Type B")
                        .description("Super B")
                        .price(25000F)
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                FishCategory fishC = FishCategory.builder()
                        .name("Type C")
                        .description("Super C")
                        .price(10000F)
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
                        .price(100000f)
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                HealthServiceCategory healthServiceCategoryB = HealthServiceCategory.builder()
                        .serviceName("Add salt or anti-stress medication")
                        .serviceDescription("Reduce stress and risk of infection.")
                        .price(200000f)
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                HealthServiceCategory healthServiceCategoryC = HealthServiceCategory.builder()
                        .serviceName("Maintain proper temperature and lighting")
                        .serviceDescription("Stabilize the transport environment")
                        .price(300000f)
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                HealthServiceCategory healthServiceCategoryD = HealthServiceCategory.builder()
                        .serviceName("Use insulated boxes")
                        .serviceDescription("Protect fish from thermal shock and vibration.")
                        .price(400000f)
                        .status(SystemStatusEnum.AVAILABLE)
                        .build();
                HealthServiceCategory healthServiceCategoryE = HealthServiceCategory.builder()
                        .serviceName("Tracking and post-shipment support")
                        .serviceDescription("Update information and instruct recipients on how to care for fish.")
                        .price(500000f)
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
