package com.swp391.group7.KoiDeliveryOrderingSystem.service;


import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Report;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.request.AnswerReportRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.request.CreateReportRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ReportResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.OrderRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.ReportRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private AccountUtils accountUtils;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ReportRepository reportRepository;

    public ReportResponse createReport(Integer orderId, CreateReportRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Report report = Report.builder()
                .users(users)
                .orders(orders)
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
        reportRepository.save(report);
        return convertToReportResponse(report);
    }

    public ReportResponse answerReport(Integer reportId, AnswerReportRequest request) {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
        report.setAnswer(request.getAnswer());
        reportRepository.save(report);
        return convertToReportResponse(report);
    }

    public List<ReportResponse> viewAll() {
        List<Report> reportList = reportRepository.findAll();
        return convertToListReportResponse(reportList);
    }

    public List<ReportResponse> viewByOrder(Integer orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<Report> reportList = reportRepository.findByOrders(orders);
        return convertToListReportResponse(reportList);
    }

    public List<ReportResponse> viewByUser() {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        List<Report> reportList = reportRepository.findByUsers(users);
        return convertToListReportResponse(reportList);
    }

    public ReportResponse deleteReport(Integer reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
        reportRepository.delete(report);
        return convertToReportResponse(report);
    }

    public ReportResponse convertToReportResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .orderId(report.getOrders().getId())
                .userId(report.getUsers().getId())
                .title(report.getTitle())
                .description(report.getDescription())
                .answer(report.getAnswer())
                .build();
    }

    public List<ReportResponse> convertToListReportResponse(List<Report> reportList) {
        List<ReportResponse> reportResponseList = new ArrayList<>();
        for (Report report : reportList) {
            reportResponseList.add(convertToReportResponse(report));
        }
        return reportResponseList;
    }
}
