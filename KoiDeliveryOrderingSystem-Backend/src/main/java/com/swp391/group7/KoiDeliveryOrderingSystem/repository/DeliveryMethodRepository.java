package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.DeliveryMethod;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Integer> {
    DeliveryMethod findByDeliveryName(String deliveryMethod);
}
