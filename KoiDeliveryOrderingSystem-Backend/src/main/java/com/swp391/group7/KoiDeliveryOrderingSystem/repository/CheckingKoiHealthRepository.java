package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.CheckingKoiHealth;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckingKoiHealthRepository extends JpaRepository<CheckingKoiHealth, Integer> {
    List<CheckingKoiHealth> findByFishProfile(FishProfile fishProfile);

    boolean existsByFishProfile(FishProfile fishProfile);

    Optional<CheckingKoiHealth> findTopByFishProfileOrderByCreateAtDesc(FishProfile fishProfile);

    List<CheckingKoiHealth> findByCreateBy(Integer createBy);
}
