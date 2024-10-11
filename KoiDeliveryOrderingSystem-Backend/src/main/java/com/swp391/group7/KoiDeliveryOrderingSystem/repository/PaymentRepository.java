package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Customers;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomers(Customers customers);
    List<Payment> findByOrders(Orders orders);
    List<Payment> findByCustomersAndOrders(Customers customers, Orders orders);
}
