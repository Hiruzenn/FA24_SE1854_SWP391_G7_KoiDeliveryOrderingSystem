package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FishProfileRepository extends JpaRepository<FishProfile, Integer> {
Optional<FishProfile> findByIdAndStatus(Integer id, SystemStatusEnum status);
List<FishProfile> findByStatus(SystemStatusEnum status);
}
