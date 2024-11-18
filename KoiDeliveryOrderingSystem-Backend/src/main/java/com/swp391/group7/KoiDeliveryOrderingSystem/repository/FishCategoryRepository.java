package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FishCategoryRepository extends JpaRepository<FishCategory, Integer> {
    Optional<FishCategory> findByName(String name);

    Optional<FishCategory> findByIdAndStatus(Integer id, SystemStatusEnum status);

    List<FishCategory> findByStatus(SystemStatusEnum status);

    Optional<FishCategory> findByNameAndStatus(String name, SystemStatusEnum status);
}
