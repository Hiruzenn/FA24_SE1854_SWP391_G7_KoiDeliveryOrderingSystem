package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

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
    List<Orders> findByUsersAndStatus(Users users, SystemStatusEnum status);
    List<Orders> findByStatus(SystemStatusEnum status);
    Optional<Orders> findByIdAndStatus(Integer id, SystemStatusEnum status);
    List<Orders> findByStatusAndCreateAtBetween(SystemStatusEnum status, LocalDateTime start, LocalDateTime end);
    @Query(value= "SELECT o.orders_id, o.total_amount FROM orders o " +
            " where o.orders_id= :orderId", nativeQuery= true )
    Object findTotalAmount(@Param("orderId") int orderId);
}
