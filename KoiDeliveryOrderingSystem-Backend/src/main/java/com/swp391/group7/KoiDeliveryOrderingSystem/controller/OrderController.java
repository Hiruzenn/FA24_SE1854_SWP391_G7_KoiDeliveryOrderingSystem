package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order.CreateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order.UpdateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        var result = orderService.createOrder(createOrderRequest);
        return ResponseEntity.ok(ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order Created")
                .result(result)
                .build());
    }

    @PutMapping("update/{OrderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(@Valid @PathVariable Integer OrderId, @RequestBody UpdateOrderRequest updateOrderRequest) {
        var result = orderService.updateOrder(OrderId, updateOrderRequest);
        return ResponseEntity.ok(ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order Updated")
                .result(result)
                .build());
    }

    @GetMapping("get-all")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        var result = orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.<List<OrderResponse>>builder()
                .code(200)
                .message("Order List")
                .result(result)
                .build());
    }

    @GetMapping("get-by-customer")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrderByCustomerId() {
        var result = orderService.getOrderByCustomerId();
        return ResponseEntity.ok(ApiResponse.<List<OrderResponse>>builder()
                .code(200)
                .message("Order List By Customer")
                .result(result)
                .build());
    }

    @PutMapping("delete/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> deleteOrder(@PathVariable Integer orderId) {
        var result = orderService.deleteOrder(orderId);
        return ResponseEntity.ok(ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order Deleted")
                .result(result)
                .build());
    }
    @PutMapping("acceptorder/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> acceptOrder(@PathVariable Integer orderId) {
        var result = orderService.AcceptOrder(orderId);
        return ResponseEntity.ok(ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order is accept")
                .result(result)
                .build());
    }

}
