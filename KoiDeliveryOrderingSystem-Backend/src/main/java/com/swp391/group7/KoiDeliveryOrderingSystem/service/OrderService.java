package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.DeliveryMethod;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.FishProfile;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order.CreateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order.UpdateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.FishProfileResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.OrderStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.DeliveryMethodRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import jakarta.persistence.criteria.Order;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryMethodRepository deliveryMethodRepository;

    public static final String RANDOM_STRING = "0123456789";

    @Autowired
    private AccountUtils accountUtils;

    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findByDeliveryMethodName(createOrderRequest.getDeliveryMethod());
        if (deliveryMethod == null) {
            throw new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND);
        }
        Orders orders = Orders.builder()
                .orderCode(generateOrderCode())
                .users(users)
                .deliveryMethod(deliveryMethod)
                .orderDate(LocalDateTime.now())
                .destination(createOrderRequest.getDestination())
                .departure(createOrderRequest.getDeparture())
                .distance(createOrderRequest.getDistance())
                .phone(createOrderRequest.getPhone())
                .amount(createOrderRequest.getAmount())
                .vat(createOrderRequest.getVat())
                .vatAmount(createOrderRequest.getVatAmount())
                .totalAmount((createOrderRequest.getTotalAmount()))
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(OrderStatusEnum.PENDING)
                .build();
        orderRepository.save(orders);
        return convertOrderToResponse(orders);
    }

    public OrderResponse updateOrder(Integer OrderId, UpdateOrderRequest updateOrderRequest) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findById(OrderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findByDeliveryMethodName(updateOrderRequest.getDeliveryMethod());
        if (deliveryMethod == null) {
            throw new AppException(ErrorCode.DELIVERY_METHOD_NOT_FOUND);
        }
        orders.setDeliveryMethod(deliveryMethod);
        orders.setDestination(updateOrderRequest.getDestination());
        orders.setDeparture(updateOrderRequest.getDeparture());
        orders.setDistance(updateOrderRequest.getDistance());
        orders.setPhone(updateOrderRequest.getPhone());
        orders.setAmount(updateOrderRequest.getAmount());
        orders.setVat(updateOrderRequest.getVat());
        orders.setVatAmount(updateOrderRequest.getVatAmount());
        orders.setTotalAmount(updateOrderRequest.getTotalAmount());
        orders.setReceivingDate(updateOrderRequest.getReceivingDate());
        orders.setEstimateDeliveryDate(updateOrderRequest.getEstimateDeliveryDate());
        orders.setUpdateAt(LocalDateTime.now());
        orders.setUpdateBy(users.getId());
        orderRepository.save(orders);

        return convertOrderToResponse(orders);
    }

    public List<OrderResponse> getAllOrders() {
        List<Orders> orders = orderRepository.findByStatus(OrderStatusEnum.AVAILABLE);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Orders order : orders) {
            orderResponses.add(convertOrderToResponse(order));
        }
        return orderResponses;
    }

    public List<OrderResponse> getOrderByCustomerId() {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        List<Orders> ordersList = orderRepository.findByUsersAndStatus(users, OrderStatusEnum.AVAILABLE);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Orders order : ordersList) {
            orderResponses.add(convertOrderToResponse(order));
        }
        return orderResponses;
    }

    public OrderResponse deleteOrder(Integer OrderId) {
        Users users = accountUtils.getCurrentUser();
        if(users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(OrderId, OrderStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        orders.setStatus(OrderStatusEnum.NOT_AVAILABLE);
        orders.setUpdateAt(LocalDateTime.now());
        orders.setUpdateBy(users.getId());
        orderRepository.save(orders);
        return convertOrderToResponse(orders);
    }

    public OrderResponse AcceptOrder(Integer OrderId) {
        Users users = accountUtils.getCurrentUser();
        if(users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findByIdAndStatus(OrderId, OrderStatusEnum.PENDING)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        orders.setStatus(OrderStatusEnum.AVAILABLE);
        orders.setUpdateAt(LocalDateTime.now());
        orders.setUpdateBy(users.getId());
        orderRepository.save(orders);
        return convertOrderToResponse(orders);
    }

    public List<OrderResponse> orderIn7days() {
        LocalDateTime sevenDayAgo = LocalDateTime.now().minusDays(7);
        List<Orders> recentOrders = orderRepository.findByOrderDateAfter(sevenDayAgo);
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Orders orders : recentOrders) {
            orderResponseList.add(convertOrderToResponse(orders));
        }
        return orderResponseList;

    }

    public List<OrderResponse> orderIn30days() {
        LocalDateTime sevenDayAgo = LocalDateTime.now().minusDays(30);
        List<Orders> recentOrders = orderRepository.findByOrderDateAfter(sevenDayAgo);
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Orders orders : recentOrders) {
            orderResponseList.add(convertOrderToResponse(orders));
        }
        return orderResponseList;

    }

    private OrderResponse convertOrderToResponse(Orders orders) {
        return OrderResponse.builder()
                .id(orders.getId())
                .orderCode(orders.getOrderCode())
                .orderDate(orders.getOrderDate())
                .deliveryMethod(orders.getDeliveryMethod().getDeliveryMethodName())
                .destination(orders.getDestination())
                .departure(orders.getDeparture())
                .distance(orders.getDistance())
                .estimateDeliveryDate(orders.getEstimateDeliveryDate())
                .receivingDate(orders.getReceivingDate())
                .phone(orders.getPhone())
                .amount(orders.getAmount())
                .vat(orders.getVat())
                .vatAmount(orders.getVatAmount())
                .totalAmount(orders.getTotalAmount())
                .createAt(orders.getCreateAt())
                .createBy(orders.getCreateBy())
                .updateAt(orders.getUpdateAt())
                .updateBy(orders.getUpdateBy())
                .status(orders.getStatus())
                .build();
    }

    private String generateOrderCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder;
        String orderCode = "";
        do {
            stringBuilder = new StringBuilder("ORD");
            for (int i = 0; i < 5; i++) {
                int randomIndex = random.nextInt(5);
                stringBuilder.append(RANDOM_STRING.charAt(randomIndex));
            }
            orderCode = stringBuilder.toString();
        } while (orderRepository.existsByOrderCode(orderCode));
        return orderCode;
    }
}
