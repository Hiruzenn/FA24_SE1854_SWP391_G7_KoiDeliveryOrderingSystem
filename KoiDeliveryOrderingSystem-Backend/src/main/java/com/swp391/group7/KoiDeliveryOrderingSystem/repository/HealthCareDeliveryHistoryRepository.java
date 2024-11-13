package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthCareDeliveryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HealthCareDeliveryHistoryRepository extends JpaRepository<HealthCareDeliveryHistory, Integer> {
    List<HealthCareDeliveryHistory> findByStatus(SystemStatusEnum status);
    Optional<HealthCareDeliveryHistory> findByIdAndStatus(Integer id, SystemStatusEnum status);
    List<HealthCareDeliveryHistory> findByHandoverDocumentAndStatus(HandoverDocument handoverDocument, SystemStatusEnum status);
}
