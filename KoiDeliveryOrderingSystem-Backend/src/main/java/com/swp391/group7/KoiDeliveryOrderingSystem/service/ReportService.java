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
        String subject = "Phản hồi báo cáo của bạn";

        String message = "<html>" +
                "<body style='font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;'>" +
                "<table align='center' width='600' style='border-collapse: collapse; background-color: #ffffff; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: 20px auto;'>" +
                "    <tr>" +
                "        <td style='padding: 20px; text-align: center; background-color: #0078d4; color: #ffffff;'>" +
                "            <h2 style='margin: 0;'>Phản Hồi Báo Cáo</h2>" +
                "        </td>" +
                "    </tr>" +
                "    <tr>" +
                "        <td style='padding: 20px;'>" +
                "            <p style='font-size: 16px; line-height: 1.5;'>Chào <strong>" + report.getUsers().getName() + "</strong>,</p>" +
                "            <p style='font-size: 16px; line-height: 1.5;'>Chúng tôi rất tiếc về những sự cố xảy ra với đơn hàng của bạn.</p>" +
                "            <p style='font-size: 16px; line-height: 1.5;'>Chúng tôi giải đáp vấn đề của bạn như sau:</p>" +
                "            <p style='font-size: 16px; line-height: 1.5; background-color: #f9f9f9; padding: 10px; border-left: 4px solid #0078d4;'>" +
                "                " + request.getAnswer() +
                "            </p>" +
                "            <p style='font-size: 16px; line-height: 1.5;'>Nếu bạn không hài lòng với câu trả lời, hãy liên hệ với chúng tôi để được giải quyết chi tiết.</p>" +
                "            <p style='font-size: 16px; line-height: 1.5;'>Thông tin liên hệ:</p>" +
                "            <ul style='font-size: 16px; line-height: 1.5; list-style: none; padding: 0;'>" +
                "                <li>Email: <a href='mailto:" + users.getEmail() + "' style='color: #0078d4; text-decoration: none;'>" + users.getEmail() + "</a></li>" +
                "                <li>Số điện thoại: <a href='tel:" + users.getPhone() + "' style='color: #0078d4; text-decoration: none;'>" + users.getPhone() + "</a></li>" +
                "            </ul>" +
                "        </td>" +
                "    </tr>" +
                "    <tr>" +
                "        <td style='padding: 20px; text-align: center; font-size: 14px; color: #555555; background-color: #f4f4f4;'>" +
                "            <p style='margin: 0;'>Trân trọng,<br><strong>Đội ngũ hỗ trợ</strong></p>" +
                "        </td>" +
                "    </tr>" +
                "</table>" +
                "</body>" +
                "</html>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setTo(report.getUsers().getEmail());
        helper.setSubject(subject);
        helper.setText(message, true);

        mailSender.send(mimeMessage);
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

    public void deleteReport(Integer reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
        reportRepository.delete(report);
    }

    public ReportResponse convertToReportResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .orderId(report.getOrders().getOrderCode())
                .userId(report.getUsers().getEmail())
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
