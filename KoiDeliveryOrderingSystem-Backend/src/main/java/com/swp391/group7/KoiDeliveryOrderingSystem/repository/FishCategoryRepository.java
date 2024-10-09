package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishCategoryRepository extends JpaRepository<FishCategory, Integer> {
    FishCategory findByFishCategoryName(String fishCategoryName);
}
