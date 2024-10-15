package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.orderdetail.CreateOrderDetailRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.orderdetail.UpdateOrderDetailRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderDetailResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.OrderDetail;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
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

    public OrderDetailResponse createOrderDetail(Integer orderId, Integer fishProfileId, CreateOrderDetailRequest createOrderDetailRequest) {
        Users users = accountUtils.getCurrentUser();
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (users == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        FishProfile fishProfile = fishProfileRepository.findById(fishProfileId).orElseThrow(() -> new AppException(ErrorCode.FISH_PROFILE_NOT_FOUND));
        OrderDetail orderDetail = OrderDetail.builder()
                .orders(orders)
                .fishProfiles(fishProfile)
                .quantity(createOrderDetailRequest.getQuantity())
                .unitPrice(createOrderDetailRequest.getUnitPrice())
                .amount(createOrderDetailRequest.getQuantity() * createOrderDetailRequest.getUnitPrice())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        orderDetailRepository.save(orderDetail);
        return convertToOrderDetailResponse(orderDetail);
    }

    public OrderDetailResponse updateOrderDetail(Integer orderDetailId, UpdateOrderDetailRequest updateOrderDetailRequest){
        Users users = accountUtils.getCurrentUser();
        if (users == null ){
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));

        orderDetail.setQuantity(updateOrderDetailRequest.getQuantity());
        orderDetail.setUnitPrice(updateOrderDetailRequest.getUnitPrice());
        orderDetail.setAmount(updateOrderDetailRequest.getQuantity() * updateOrderDetailRequest.getUnitPrice());
        orderDetail.setUpdateAt(LocalDateTime.now());
        orderDetail.setUpdateBy(users.getId());

        orderDetailRepository.save(orderDetail);
        return convertToOrderDetailResponse(orderDetail);
    }

    public List<OrderDetailResponse> getOrderDetailsByOrderId(Integer orderId){
        Users users = accountUtils.getCurrentUser();
        if (users == null ){
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrders(orders);
        List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetailResponseList.add(convertToOrderDetailResponse(orderDetail));
        }
        return orderDetailResponseList;
    }

    public OrderDetailResponse convertToOrderDetailResponse(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .fishProfile(orderDetail.getFishProfiles())
                .unitPrice(orderDetail.getUnitPrice())
                .amount(orderDetail.getAmount())
                .createAt(orderDetail.getCreateAt())
                .createBy(orderDetail.getCreateBy())
                .updateAt(orderDetail.getUpdateAt())
                .updateBy(orderDetail.getUpdateBy())
                .status(orderDetail.getStatus())
                .build();
    }
}

