package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.request.AnswerReportRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.request.CreateReportRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ReportResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("report")
@Tag(name = "Report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("create/{orderId}")
    public ResponseEntity<ApiResponse<ReportResponse>> create(@PathVariable("orderId") Integer orderId, @RequestBody CreateReportRequest request) {
        var result = reportService.createReport(orderId, request);
        return ResponseEntity.ok(ApiResponse.<ReportResponse>builder()
                .code(200)
                .message("Thank You for reporting, we will answer soon!")
                .result(result)
                .build());
    }

    @PutMapping("answer/{reportId}")
    public ResponseEntity<ApiResponse<ReportResponse>> answer(@PathVariable("reportId") Integer reportId, @RequestBody AnswerReportRequest request) throws MessagingException {
        var result = reportService.answerReport(reportId, request);
        return ResponseEntity.ok(ApiResponse.<ReportResponse>builder()
                .code(200)
                .message("The report has been answered successfully")
                .result(result)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<ReportResponse>>> viewAll() {
        var result = reportService.viewAll();
        return ResponseEntity.ok(ApiResponse.<List<ReportResponse>>builder()
                .code(200)
                .message("All Report in System")
                .result(result)
                .build());
    }

    @GetMapping("view-by-order/{orderId}")
    public ResponseEntity<ApiResponse<List<ReportResponse>>> viewByOrder(@PathVariable("orderId") Integer orderId) {
        var result = reportService.viewByOrder(orderId);
        return ResponseEntity.ok(ApiResponse.<List<ReportResponse>>builder()
                .code(200)
                .message("View Report By Order")
                .result(result)
                .build());
    }

    @GetMapping("view-by-user")
    public ResponseEntity<ApiResponse<List<ReportResponse>>> viewByUser() {
        var result = reportService.viewByUser();
        return ResponseEntity.ok(ApiResponse.<List<ReportResponse>>builder()
                .code(200)
                .message("View Report By Order")
                .result(result)
                .build());
    }

    @DeleteMapping("delete/{reportId}")
    public ResponseEntity<ApiResponse<Void>> deleteReport(@PathVariable("reportId") Integer reportId) {
        reportService.deleteReport(reportId);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Delete Successfully")
                .build());
    }
}
