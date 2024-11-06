package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.InvoiceStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Invoice;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByUsers(Users users);

    Invoice findByOrdersAndStatus(Orders orders, InvoiceStatusEnum status);

    Optional<Invoice> findByIdAndStatus(Integer id, InvoiceStatusEnum status);
}
