package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.DeliveryMethod;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Integer> {
    Optional<DeliveryMethod> findByIdAndStatus(Integer id, SystemStatusEnum status);

    List<DeliveryMethod> findByStatus(SystemStatusEnum status);

    Optional<DeliveryMethod> findByNameAndStatus(String deliveryMethodName, SystemStatusEnum status);
}
