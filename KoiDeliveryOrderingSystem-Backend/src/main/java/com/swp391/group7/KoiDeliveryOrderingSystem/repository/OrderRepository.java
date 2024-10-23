package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    boolean existsByOrderCode(String orderCode);
    Optional<Orders> findByIdAndUsers(int id, Users users);
    List<Orders> findByUsersAndStatus(Users users, SystemStatusEnum status);
    List<Orders> findByStatus(SystemStatusEnum status);
    Optional<Orders> findByUsers(Users users);

    List<Orders> findByOrderDateAfter (LocalDateTime date);
}
