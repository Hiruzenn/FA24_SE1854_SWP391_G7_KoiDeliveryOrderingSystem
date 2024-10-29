package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.orderdetail.CreateOrderDetailRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.orderdetail.UpdateOrderDetailRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.OrderDetailResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order-detail")
@RequiredArgsConstructor
public class OrderDetailController {
    @Autowired
    private final OrderDetailService orderDetailService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> createOrderDetail(@RequestBody CreateOrderDetailRequest createOrderDetailRequest) {
        var result = orderDetailService.createOrderDetail(createOrderDetailRequest);
        return ResponseEntity.ok(ApiResponse.<OrderDetailResponse>builder()
                .code(200)
                .message("OrderDetail created successfully")
                .result(result)
                .build());
    }

    @PutMapping("update/{orderDetailId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> updateOrderDetail (@PathVariable Integer orderDetailId, @RequestBody UpdateOrderDetailRequest updateOrderDetailRequest){
        var result = orderDetailService.updateOrderDetail(orderDetailId, updateOrderDetailRequest);
        return ResponseEntity.ok(ApiResponse.<OrderDetailResponse>builder()
                .code(200)
                .message("OrderDetail updated successfully")
                .result(result)
                .build());
    }

    @GetMapping("view-by-order-id/{orderId}")
    public ResponseEntity<ApiResponse<List<OrderDetailResponse>>> getOrderDetailByOrderId (@PathVariable Integer orderId){
        var result = orderDetailService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.<List<OrderDetailResponse>>builder()
                .code(200)
                .message("OrderDetail view successfully")
                .result(result)
                .build());
    }

    @PutMapping("delete/{orderDetailId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> deleteOrderDetail (@PathVariable Integer orderDetailId){
        var result = orderDetailService.deleteOrderDetail(orderDetailId);
        return ResponseEntity.ok(ApiResponse.<OrderDetailResponse>builder()
                .code(200)
                .message("OrderDetail deleted")
                .result(result)
                .build());
    }
}
