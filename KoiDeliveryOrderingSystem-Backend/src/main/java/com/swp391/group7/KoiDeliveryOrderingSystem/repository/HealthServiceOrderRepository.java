package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceCategory;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.HealthServiceOrder;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthServiceOrderRepository extends JpaRepository<HealthServiceOrder, Integer> {
    List<HealthServiceOrder> findHealthServiceOrderByOrders(Orders orders);
    HealthServiceOrder findHealthServiceOrderByHealthServiceCategoryAndOrdersAndStatus(HealthServiceCategory healthServiceCategory, Orders orders, SystemStatusEnum status);
    Boolean existsHealthServiceOrderByHealthServiceCategoryAndOrdersAndStatus(HealthServiceCategory healthServiceCategory, Orders orders, SystemStatusEnum status);
}
