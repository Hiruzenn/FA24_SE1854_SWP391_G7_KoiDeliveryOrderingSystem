package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.CreateOrderDetailRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.CreateOrderRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.request.UpdateOrderDetailRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.dto.response.OrderDetailResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order-detail")
@RequiredArgsConstructor
public class OrderDetailController {
    @Autowired
    private final OrderDetailService orderDetailService;

    @PostMapping("create/{orderId}/{fishProfileId}")
    public ApiResponse<OrderDetailResponse> createOrderDetail(@PathVariable("orderId") Integer orderDetailId, @PathVariable("fishProfileId") Integer fishProfileId,
                                                        @RequestBody CreateOrderDetailRequest createOrderDetailRequest) {
        var result = orderDetailService.createOrderDetail(orderDetailId, fishProfileId, createOrderDetailRequest);
        return ApiResponse.<OrderDetailResponse>builder()
                .code(200)
                .message("OrderDetail created successfully")
                .result(result)
                .build();
    }

    @PutMapping("update/{orderDetailId}")
    public ApiResponse<OrderDetailResponse> updateOrderDetail (@PathVariable Integer orderDetailId, @RequestBody UpdateOrderDetailRequest updateOrderDetailRequest){
        var result = orderDetailService.updateOrderDetail(orderDetailId, updateOrderDetailRequest);
        return ApiResponse.<OrderDetailResponse>builder()
                .code(200)
                .message("OrderDetail updated successfully")
                .result(result)
                .build();
    }

    @GetMapping("view-order-detail-by-order-id/{orderId}")
    public ApiResponse<List<OrderDetailResponse>> getOrderDetailByOrderId (@PathVariable Integer orderId){
        var result = orderDetailService.getOrderDetailsByOrderId(orderId);
        return ApiResponse.<List<OrderDetailResponse>>builder()
                .code(200)
                .message("OrderDetail view successfully")
                .result(result)
                .build();
    }
}
