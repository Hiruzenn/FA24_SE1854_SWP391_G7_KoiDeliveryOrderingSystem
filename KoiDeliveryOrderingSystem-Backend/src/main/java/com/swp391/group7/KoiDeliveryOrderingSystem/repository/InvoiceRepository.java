package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

}
