package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Certificate;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    List<Certificate> findCertificateByStatus(SystemStatusEnum status);
    Optional<Certificate> findCertificateByIdAndStatus(Integer id, SystemStatusEnum status);
}
