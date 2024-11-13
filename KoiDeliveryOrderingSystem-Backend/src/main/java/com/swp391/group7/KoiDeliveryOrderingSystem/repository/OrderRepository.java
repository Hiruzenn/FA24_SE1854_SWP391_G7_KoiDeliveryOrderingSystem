package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    boolean existsByOrderCode(String orderCode);

    List<Orders> findByUsersAndStatus(Users users, OrderStatusEnum status);
    List<Orders> findByStatus(OrderStatusEnum status);
    Optional<Orders> findByIdAndStatus(Integer id, OrderStatusEnum status);
    List<Orders> findByStatusAndCreateAtBetween(OrderStatusEnum status, LocalDateTime start, LocalDateTime end);
}
