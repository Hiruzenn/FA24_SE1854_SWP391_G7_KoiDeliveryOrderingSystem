package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CheckingKoiHealth;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.OrderDetail;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckingKoiHealthRepository extends JpaRepository<CheckingKoiHealth, Integer> {
    List<CheckingKoiHealth> findByPackagesAndStatus(Package packages, SystemStatusEnum status);

    List<CheckingKoiHealth> findByOrderDetailAndStatus(OrderDetail orderDetail, SystemStatusEnum status);

    List<CheckingKoiHealth> findByStatus(SystemStatusEnum status);

    Optional<CheckingKoiHealth> findByIdAndStatus(Integer id, SystemStatusEnum status);
}
