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
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    @Autowired
    private JavaMailSenderImpl mailSender;

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

    public ReportResponse answerReport(Integer reportId, AnswerReportRequest request) throws MessagingException {
        Users users = accountUtils.getCurrentUser();
        if (users == null) {
            throw new AppException(ErrorCode.NOT_LOGIN);
        }
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
        report.setAnswer(request.getAnswer());
        reportRepository.save(report);
//        String subject = "Phản hồi báo cáo của bạn";
//
//        String message = "<html>" +
//                "<body>" +
//                "<h2>Xác thực tài khoản của bạn</h2>" +
//                "<p>Chào " + users.getName() + ",</p>" +
//                "<p>Cảm ơn bạn đã đăng ký tài khoản với chúng tôi! Để hoàn tất quá trình đăng ký, vui lòng nhấn vào liên kết bên dưới để xác thực tài khoản của bạn:</p>" +
//                "<p>Trân trọng,<br>Đội ngũ hỗ trợ</p>" +
//                "</body>" +
//                "</html>";
//
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
//        helper.setTo(email);
//        helper.setSubject(subject);
//        helper.setText(message, true);
//
//        mailSender.send(mimeMessage);
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
