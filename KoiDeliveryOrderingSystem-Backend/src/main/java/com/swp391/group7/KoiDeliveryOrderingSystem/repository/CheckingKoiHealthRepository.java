package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CheckingKoiHealth;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.OrderDetail;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingKoiHealthRepository extends JpaRepository<CheckingKoiHealth, Integer> {
    List<CheckingKoiHealth> findByPackages(Package packages);
    List<CheckingKoiHealth> findByOrderDetail(OrderDetail orderDetail);
}
