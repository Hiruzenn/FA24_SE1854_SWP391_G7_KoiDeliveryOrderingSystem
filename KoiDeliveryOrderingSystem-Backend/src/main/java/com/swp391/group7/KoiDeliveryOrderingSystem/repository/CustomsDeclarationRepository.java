package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CustomsDeclaration;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomsDeclarationRepository extends JpaRepository<CustomsDeclaration, Integer> {
    Optional<CustomsDeclaration> findByOrders(Orders orders);
}
