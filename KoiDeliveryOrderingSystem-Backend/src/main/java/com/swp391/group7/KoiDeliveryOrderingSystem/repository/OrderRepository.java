package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Customers;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    boolean existsByOrderCode(String orderCode);
    Optional<Orders> findByIdAndCustomers(int id, Customers customer);
    List<Orders> findByCustomersAndStatus(Customers customer, SystemStatusEnum status);
    List<Orders> findByStatus(SystemStatusEnum status);
    Optional<Orders> findByCustomers(Customers customer);

}
