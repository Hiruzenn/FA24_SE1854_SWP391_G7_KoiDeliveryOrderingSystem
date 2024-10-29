package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HandoverDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HandoverDocumentRepository extends JpaRepository<HandoverDocument, Integer> {
    Optional<HandoverDocument> findByIdAndStatus(Integer id, SystemStatusEnum status);
    List<HandoverDocument> findByStatus(SystemStatusEnum status);
}
