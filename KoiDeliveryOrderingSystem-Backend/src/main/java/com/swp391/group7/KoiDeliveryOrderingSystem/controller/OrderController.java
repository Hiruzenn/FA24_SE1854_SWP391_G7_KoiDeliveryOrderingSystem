package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.CreateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.UpdateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.OrderResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("create")
    ApiResponse<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest){
        var result = orderService.createOrder(createOrderRequest);
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order Created")
                .result(result)
                .build();
    }

    @PutMapping("update/{OrderId}")
    ApiResponse<OrderResponse> updateOrder(@PathVariable Integer OrderId, @RequestBody UpdateOrderRequest updateOrderRequest){
        var result = orderService.updateOrder(OrderId, updateOrderRequest);
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order Updated")
                .result(result)
                .build();
    }

    @GetMapping("get-all")
    ApiResponse<List<OrderResponse>> getAllOrders(){
        var result = orderService.getAllOrders();
        return ApiResponse.<List<OrderResponse>>builder()
                .code(200)
                .message("Order List")
                .result(result)
                .build();
    }

    @GetMapping("get-by-customer")
    ApiResponse<List<OrderResponse>> getOrderByCustomerId(){
        var result = orderService.getOrderByCustomerId();
        return ApiResponse.<List<OrderResponse>>builder()
                .code(200)
                .message("Order List By Customer")
                .result(result)
                .build();
    }

    @PutMapping("delete/{orderId}")
    ApiResponse<String> deleteOrder(@PathVariable Integer orderId){
        var result = orderService.deleteOrder(orderId);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Order Deleted")
                .result(result)
                .build();
    }
}
