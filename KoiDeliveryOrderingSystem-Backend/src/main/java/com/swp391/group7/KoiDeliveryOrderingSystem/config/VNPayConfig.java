package com.swp391.group7.KoiDeliveryOrderingSystem.config;

import com.swp391.group7.KoiDeliveryOrderingSystem.vnpay.VNPayUntil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Configuration
@PropertySource("classpath:application.properties")
public class VNPayConfig {
    @Getter
    @Value("${payment.vnPay-url}")
    private String vnpPayUrl;
    @Value("${payment.vnPay.returnUrl}")
    private String vnpReturnUrl;
    @Getter
    @Value("${payment.vnPay.tmnCode}")
    private String vnpTmnCode ;
    @Getter
    @Value("${payment.vnPay.secretKey}")
    private String secretKey;
    @Value("${payment.vnPay.version}")
    private String vnpVersion;
    @Value("${payment.vnPay.command}")
    private String vnpCommand;
    @Value("${payment.vnPay.orderType}")
    private String orderType;
    public Map<String, String> getVNPayConfig(int orderId, String username, int transactionId) {
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", this.vnpVersion);
        vnpParamsMap.put("vnp_Command", this.vnpCommand);
        vnpParamsMap.put("vnp_TmnCode", this.vnpTmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef",  VNPayUntil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" +  VNPayUntil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnpReturnUrl + "?orderId=" + orderId + "&username=" + username + "&transactionId=" + transactionId);

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(timeZone);

        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);

        calendar.add(Calendar.MINUTE, 15);
        String vnpExpireDate = formatter.format(calendar.getTime());

        vnpParamsMap.put("vnp_ExpireDate", vnpExpireDate);
        return vnpParamsMap;
    }
}
