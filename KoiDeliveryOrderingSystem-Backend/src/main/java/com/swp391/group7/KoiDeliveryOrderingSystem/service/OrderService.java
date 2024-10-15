package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order.CreateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order.UpdateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.SystemStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.DeliveryMethodRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        Orders orders = Orders.builder()
                .orderCode(generateOrderCode())
                .users(users)
                .deliveryMethod(deliveryMethodRepository.findByDeliveryName(createOrderRequest.getDeliveryMethod()))
                .orderDate(LocalDateTime.now())
                .destination(createOrderRequest.getDestination())
                .departure(createOrderRequest.getDeparture())
                .distance(createOrderRequest.getDistance())
                .phone(createOrderRequest.getPhone())
                .amount(createOrderRequest.getAmount())
                .createAt(LocalDateTime.now())
                .createBy(users.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(users.getId())
                .status(SystemStatusEnum.AVAILABLE)
                .build();
        orderRepository.save(orders);
        return convertOrderToResponse(orders);
    }

    public OrderResponse updateOrder(Integer OrderId, UpdateOrderRequest updateOrderRequest) {
        Users customer = accountUtils.getCurrentUser();
        Orders orders = orderRepository.findByIdAndUsers(OrderId, customer).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        orders.setDeliveryMethod(deliveryMethodRepository.findByDeliveryName(updateOrderRequest.getDeliveryMethod()));
        orders.setDestination(updateOrderRequest.getDestination());
        orders.setDeparture(updateOrderRequest.getDeparture());
        orders.setDistance(updateOrderRequest.getDistance());
        orders.setPhone(updateOrderRequest.getPhone());
        orders.setUpdateAt(LocalDateTime.now());
        orders.setUpdateBy(customer.getId());
        orderRepository.save(orders);

        return convertOrderToResponse(orders);
    }

    public List<OrderResponse> getAllOrders() {
        List<Orders> orders = orderRepository.findByStatus(SystemStatusEnum.AVAILABLE);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Orders order : orders) {
            orderResponses.add(convertOrderToResponse(order));
        }
        return orderResponses;
    }

    public List<OrderResponse> getOrderByCustomerId() {
        Users users = accountUtils.getCurrentUser();
        List<Orders> ordersList = orderRepository.findByUsersAndStatus(users, SystemStatusEnum.AVAILABLE);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Orders order : ordersList) {
            orderResponses.add(convertOrderToResponse(order));
        }
        return orderResponses;
    }

    public String deleteOrder(Integer OrderId) {
        Users customer = accountUtils.getCurrentUser();
        Orders orders = orderRepository.findById(OrderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (orders.getStatus() == SystemStatusEnum.AVAILABLE) {
            orders.setStatus(SystemStatusEnum.NOT_AVAILABLE);
            orders.setUpdateAt(LocalDateTime.now());
            orders.setUpdateBy(customer.getId());
            orderRepository.save(orders);
            return "Order deleted successfully";
        }else{
            return  "Order is deleted before";
        }

    }

    private OrderResponse convertOrderToResponse(Orders orders) {
        return OrderResponse.builder()
                .orderCode(orders.getOrderCode())
                .orderDate(orders.getOrderDate())
                .deliveryMethod(orders.getDeliveryMethod().getDeliveryName())
                .destination(orders.getDestination())
                .departure(orders.getDeparture())
                .distance(orders.getDistance())
                .estimateDeliveryDate(orders.getEstimateDeliveryDate())
                .receivingDate(orders.getReceivingDate())
                .phone(orders.getPhone())
                .amount(orders.getAmount())
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
