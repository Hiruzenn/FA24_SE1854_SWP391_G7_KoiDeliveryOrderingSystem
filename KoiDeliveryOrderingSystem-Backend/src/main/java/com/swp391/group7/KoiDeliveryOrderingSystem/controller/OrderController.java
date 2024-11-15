package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order.CreateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.order.UpdateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Tag(name = "Order")
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

    @PutMapping("calculate/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> calculateOrder(@PathVariable Integer orderId) {
        var result = orderService.calculateOrder(orderId);
        return ResponseEntity.ok(ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Calculated Order")
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

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        var result = orderService.getAll();
        return ResponseEntity.ok(ApiResponse.<List<OrderResponse>>builder()
                .code(200)
                .message("Order Available List")
                .result(result)
                .build());
    }

    @GetMapping("view-by-customer")
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
}
