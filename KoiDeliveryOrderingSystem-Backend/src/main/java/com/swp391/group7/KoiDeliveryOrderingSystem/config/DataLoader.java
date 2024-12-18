package com.swp391.group7.KoiDeliveryOrderingSystem.config;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.CustomerStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.*;
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
    String encodedPassword = passwordEncoder.encode("123456");

    @Bean
    CommandLineRunner initData(UserRepository userRepository, RoleRepository roleRepository,
                               DeliveryMethodRepository deliveryMethodRepository, FishCategoryRepository fishCategoryRepository,
                               HealthServiceCategoryRepository healthServiceCategoryRepository,
                               FishProfileRepository fishProfileRepository) {
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
                        .phone("123456789")
                        .address("Dia Chi 123")
                        .createAt(LocalDateTime.now())
                        .updateAt(LocalDateTime.now())
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users staff = Users.builder()
                        .name("SaleStaff")
                        .email("SaleStaff@gmail.com")
                        .password(encodedPassword)
                        .phone("123456789")
                        .address("Dia Chi 123")
                        .createAt(LocalDateTime.now())
                        .updateAt(LocalDateTime.now())
                        .role(roleRepository.findByName("SALE_STAFF"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users manager = Users.builder()
                        .name("Manager")
                        .email("Manager@gmail.com")
                        .password(encodedPassword)
                        .phone("123456789")
                        .address("Dia Chi 123")
                        .createAt(LocalDateTime.now())
                        .updateAt(LocalDateTime.now())
                        .role(roleRepository.findByName("MANAGER"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users deliveryStaff1 = Users.builder()
                        .name("DeliveryStaff1")
                        .email("DeliveryStaff1@gmail.com")
                        .password(encodedPassword)
                        .phone("123456789")
                        .address("Dia Chi 123")
                        .createAt(LocalDateTime.now())
                        .updateAt(LocalDateTime.now())
                        .role(roleRepository.findByName("DELIVERY_STAFF"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users deliveryStaff2 = Users.builder()
                        .name("DeliveryStaff2")
                        .email("DeliveryStaff2@gmail.com")
                        .password(encodedPassword)
                        .phone("123456789")
                        .address("Dia Chi 123")
                        .createAt(LocalDateTime.now())
                        .updateAt(LocalDateTime.now())
                        .role(roleRepository.findByName("DELIVERY_STAFF"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                Users deliveryStaff3 = Users.builder()
                        .name("DeliveryStaff3")
                        .email("DeliveryStaff3@gmail.com")
                        .password(encodedPassword)
                        .phone("123456789")
                        .address("Dia Chi 123")
                        .createAt(LocalDateTime.now())
                        .updateAt(LocalDateTime.now())
                        .role(roleRepository.findByName("DELIVERY_STAFF"))
                        .customerStatus(CustomerStatusEnum.VERIFIED)
                        .build();
                userRepository.save(user);
                userRepository.save(staff);
                userRepository.save(manager);
                userRepository.save(deliveryStaff1);
                userRepository.save(deliveryStaff2);
                userRepository.save(deliveryStaff3);
            }
            if (deliveryMethodRepository.count() == 0) {
                DeliveryMethod vehicle1 = DeliveryMethod.builder()
                        .name("Xe Tải Chuyên Dụng")
                        .description("Xe tải có hệ thống điều hòa nhiệt độ, bể chứa nước và các thiết bị kiểm soát môi trường, dành cho vận chuyển dài.")
                        .price(250F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                DeliveryMethod vehicle2 = DeliveryMethod.builder()
                        .name("Xe Van Điều Hòa")
                        .description("Xe van nhỏ, trang bị điều hòa và thiết bị giữ nhiệt, thích hợp cho vận chuyển trong thành phố hoặc quãng đường ngắn.")
                        .price(150F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                DeliveryMethod vehicle3 = DeliveryMethod.builder()
                        .name("Container Chuyên Dụng")
                        .description("Container có hệ thống cấp oxy và điều chỉnh nhiệt độ, phù hợp cho vận chuyển số lượng lớn hoặc quãng đường dài.")
                        .price(350F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                DeliveryMethod vehicle4 = DeliveryMethod.builder()
                        .name("Xe Ba Gác")
                        .description("Phương tiện vận chuyển giá rẻ, thích hợp cho các chuyến giao hàng nội thành hoặc quãng đường ngắn, không cần điều hòa.")
                        .price(70F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                DeliveryMethod vehicle5 = DeliveryMethod.builder()
                        .name("Xe Máy")
                        .description("Phương tiện nhỏ, sử dụng cho giao hàng nhanh, phù hợp với số lượng ít cá koi và quãng đường ngắn.")
                        .price(30F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                DeliveryMethod vehicle6 = DeliveryMethod.builder()
                        .name("Máy Bay Chuyên Dụng")
                        .description("Dùng cho các chuyến vận chuyển quốc tế hoặc quãng đường dài, cá được bảo vệ trong các bồn oxy đặc biệt.")
                        .price(500F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                deliveryMethodRepository.save(vehicle1);
                deliveryMethodRepository.save(vehicle2);
                deliveryMethodRepository.save(vehicle3);
                deliveryMethodRepository.save(vehicle4);
                deliveryMethodRepository.save(vehicle5);
                deliveryMethodRepository.save(vehicle6);
            }
            if (fishCategoryRepository.count() == 0) {
                FishCategory fish1 = FishCategory.builder()
                        .name("Kohaku")
                        .description("Thân màu trắng với các mảng đỏ (hi). Đây là loại Koi phổ biến và được yêu thích nhất.")
                        .price(5000F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                FishCategory fish2 = FishCategory.builder()
                        .name("Taisho Sanke")
                        .description("Giống Kohaku nhưng có thêm các mảng đen (sumi) cùng với màu đỏ và trắng.")
                        .price(10000F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                FishCategory fish3 = FishCategory.builder()
                        .name("Showa Sanshoku")
                        .description("Thân đen với các mảng màu đỏ và trắng. Trông giống Sanke nhưng có nhiều màu đen hơn.")
                        .price(10000F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                FishCategory fish4 = FishCategory.builder()
                        .name("Utsurimono")
                        .description("Cá Koi đen với các mảng trắng, đỏ, hoặc vàng. Tương phản mạnh giữa màu đen và các màu khác.")
                        .price(8000F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                FishCategory fish5 = FishCategory.builder()
                        .name("Shusui")
                        .description("Thân màu xanh xám với các mảng đỏ và trắng. Không có vảy, có vẻ ngoài sạch sẽ và đặc biệt.")
                        .price(6000F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                FishCategory fish6 = FishCategory.builder()
                        .name("Asagi")
                        .description("Thân màu xanh nhạt với hoa văn lưới trên lưng và vây cùng bụng màu đỏ. Độc đáo và thanh lịch.")
                        .price(5000F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                FishCategory fish7 = FishCategory.builder()
                        .name("Tancho")
                        .description("Thân trắng với một điểm đỏ trên đầu, giống quốc kỳ Nhật Bản. Mang ý nghĩa biểu tượng và hiếm có.")
                        .price(20000F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                fishCategoryRepository.save(fish1);
                fishCategoryRepository.save(fish2);
                fishCategoryRepository.save(fish3);
                fishCategoryRepository.save(fish4);
                fishCategoryRepository.save(fish5);
                fishCategoryRepository.save(fish6);
                fishCategoryRepository.save(fish7);
            }
            if (healthServiceCategoryRepository.count() == 0) {
                HealthServiceCategory healthServiceCategoryA = HealthServiceCategory.builder()
                        .serviceName("Gói Chăm Sóc Cơ Bản")
                        .serviceDescription("Đóng gói túi nilon chịu lực cao với oxy bổ sung, kiểm tra kích thước và nhiệt độ phù hợp cho cá Koi nhỏ.")
                        .price(10000F)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                HealthServiceCategory healthServiceCategoryB = HealthServiceCategory.builder()
                        .serviceName("Gói Chăm Sóc Nâng Cao")
                        .serviceDescription("Đóng gói túi đôi với oxy lâu dài, kiểm soát nhiệt độ và thêm dung dịch giảm căng thẳng cho cá.")
                        .price(20000f)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                HealthServiceCategory healthServiceCategoryC = HealthServiceCategory.builder()
                        .serviceName("Gói Chăm Sóc Đặc Biệt")
                        .serviceDescription("Thùng chuyên dụng với kiểm soát nhiệt độ, oxy liên tục, kiểm tra trước và sau bởi chuyên gia, có bảo hiểm.")
                        .price(30000f)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                HealthServiceCategory healthServiceCategoryD = HealthServiceCategory.builder()
                        .serviceName("Gói Kiểm Tra & Phục Hồi Sức Khỏe")
                        .serviceDescription("Kiểm tra sức khỏe toàn diện sau vận chuyển, cung cấp hồ phục hồi với thuốc và hệ thống kiểm soát nước.")
                        .price(40000f)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .build();
                HealthServiceCategory healthServiceCategoryE = HealthServiceCategory.builder()
                        .serviceName("Gói Vận Chuyển Quốc Tế")
                        .serviceDescription("Đóng gói theo tiêu chuẩn quốc tế, xử lý thủ tục hải quan nhanh, kiểm soát nhiệt độ và oxy cho thời gian dài.")
                        .price(50000f)
                        .createAt(LocalDateTime.now())
                        .createBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
                        .updateAt(LocalDateTime.now())
                        .updateBy(userRepository.findByEmail("Manager@gmail.com").get().getId())
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
