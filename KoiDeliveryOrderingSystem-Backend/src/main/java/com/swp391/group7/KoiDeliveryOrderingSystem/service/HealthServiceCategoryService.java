package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.repository.HealthServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthServiceCategoryService {
    @Autowired
    private final HealthServiceCategoryRepository healthServiceCategoryRepository;

    
}
