package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HandoverStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HandoverDocumentRepository extends JpaRepository<HandoverDocument, Integer> {
    List<HandoverDocument> findByUsers(Users user);

    Optional<HandoverDocument> findByOrders(Orders order);

    Optional<HandoverDocument> findByOrdersAndHandoverStatus(Orders order, HandoverStatusEnum status);

    Boolean existsByOrders(Orders order);

    List<HandoverDocument> findByCreateBy(Integer createBy);
}
