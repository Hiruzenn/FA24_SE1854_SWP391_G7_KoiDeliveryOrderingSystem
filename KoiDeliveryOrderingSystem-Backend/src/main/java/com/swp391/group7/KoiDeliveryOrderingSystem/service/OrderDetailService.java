package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.*;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.orderdetail.CreateOrderDetailRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.orderdetail.UpdateOrderDetailRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderDetailResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishCategoryRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.FishProfileRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderDetailRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private FishProfileRepository fishProfileRepository;

    @Autowired
    private FishCategoryRepository fishCategoryRepository;

    public OrderDetailResponse createOrderDetail(CreateOrderDetailRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(request.getOrderId(), OrderStatusEnum.PENDING).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        FishProfile fishProfile = fishProfileRepository.findByIdAndStatus(request.getFishProfileId(), SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        FishCategory fishCategory = fishCategoryRepository.findByFishCategoryName(fishProfile.getType().getFishCategoryName());

        OrderDetail orderDetail = OrderDetail.builder()
                .orders(orders)
                .fishProfiles(fishProfile)
                .quantity(request.getQuantity())
                .unitPrice(fishCategory.getPrice())
                .amount(request.getQuantity() * fishCategory.getPrice())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        orderDetailRepository.save(orderDetail);
        return convertToOrderDetailResponse(orderDetail);
    }

    public OrderDetailResponse updateOrderDetail(Integer orderDetailId, UpdateOrderDetailRequest updateOrderDetailRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        OrderDetail orderDetail = orderDetailRepository.findByIdAndStatus(orderDetailId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));

        orderDetail.setQuantity(updateOrderDetailRequest.getQuantity());
        orderDetail.setAmount(updateOrderDetailRequest.getQuantity() * orderDetail.getUnitPrice());
        orderDetail.setUpdateAt(LocalDateTime.now());
        orderDetail.setUpdateBy(users.getId());
        orderDetailRepository.save(orderDetail);
        return convertToOrderDetailResponse(orderDetail);
    }

    public List<OrderDetailResponse> getOrderDetailsByOrderId(Integer orderId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(orderId, OrderStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrders(orders);
        List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetailResponseList.add(convertToOrderDetailResponse(orderDetail));
        }
        return orderDetailResponseList;
    }

    public OrderDetailResponse deleteOrderDetail(Integer orderDetailId) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        OrderDetail orderDetail = orderDetailRepository.findByIdAndStatus(orderDetailId, SystemStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));
        orderDetail.setStatus(SystemStatusEnum.NOT_AVAILABLE);
        orderDetail.setUpdateAt(LocalDateTime.now());
        orderDetail.setUpdateBy(users.getId());
        orderDetailRepository.save(orderDetail);
        return convertToOrderDetailResponse(orderDetail);
    }

    public OrderDetailResponse convertToOrderDetailResponse(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .fishProfileId(orderDetail.getFishProfiles().getId())
                .orderId(orderDetail.getOrders().getId())
                .unitPrice(orderDetail.getUnitPrice())
                .amount(orderDetail.getAmount())
                .createAt(orderDetail.getCreateAt())
                .createBy(orderDetail.getCreateBy())
                .updateAt(orderDetail.getUpdateAt())
                .updateBy(orderDetail.getUpdateBy())
                .status(orderDetail.getStatus())
                .quantity(orderDetail.getQuantity())
                .build();
    }
}

