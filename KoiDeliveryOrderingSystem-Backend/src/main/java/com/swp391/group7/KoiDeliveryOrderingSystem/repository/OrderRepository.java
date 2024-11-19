package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.DeliveryMethod;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    boolean existsByOrderCode(String orderCode);

    List<Orders> findByUsers(Users users);

    List<Orders> findByStatus(OrderStatusEnum status);

    Optional<Orders> findByIdAndStatus(Integer id, OrderStatusEnum status);

    List<Orders> findByStatusAndCreateAtBetween(OrderStatusEnum status, LocalDateTime start, LocalDateTime end);

    List<Orders> findByUsersAndStatus(Users users, OrderStatusEnum status);

    boolean existsByDeliveryMethod(DeliveryMethod deliveryMethod);
}
