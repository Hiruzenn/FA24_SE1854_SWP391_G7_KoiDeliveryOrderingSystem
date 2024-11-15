package com.swp391.group7.KoiDeliveryOrderingSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //AUTH & Customers
    INVALID_REQUEST(1000, "Invalid request"),
    ACCOUNT_REGISTERED(1001, "User existed"),
    INVALID_PASSWORD(1002, "Invalid password"),
    USER_NOT_EXISTED(1003, "User Not Found"),
    INVALID_REPEAT_PASSWORD(1004, "Invalid Re-Password"),
    UNAUTHENTICATED(1010, "Unauthenticated"),
    NOT_LOGIN(1011, "Not Login"),
    INVALID_TOKEN(1012, "Invalid Token"),
    UNVERIFIED_ACCOUNT(1013, "Unverified Account"),


    //ORDER
    ORDER_NOT_FOUND(1100, "Order Not Found"),

    //ORDER_DETAIL
    ORDER_DETAIL_NOT_FOUND(1200, "Order Detail Not Found"),

    //FISH_PROFILE
    FISH_PROFILE_NOT_FOUND(1300, "Fish Profile Not Found"),

    //Certificate
    CERTIFICATE_NOT_FOUND(1400, "Certificate not found"),
    CERTIFICATE_EXISTED(1401, "Certificate existed"),
    //DeliveryMethod
    DELIVERY_METHOD_NOT_FOUND(2000, "Delivery Method Not Found"),
    //FishCategory
    FISH_CATEGORY_NOT_FOUND(2100, "Fish Category Not Found"),
    //Package

    CUSTOM_DECLARATION_NOT_EXISTED(1600, "Customs Declaration not existed"),
    INVOICE_NOT_FOUND(1700, "Invoice not existed"),
    HANDOVER_DOCUMENT_NOT_FOUND(1800, "Handover Document not existed"),
    HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND(1900, "History not existed"),


    HEALTH_SERVICE_CATEGORY_NOT_FOUND(1501, "health service category not found"),

    //HealthServiceOrder
    HEALTH_SERVICE_ORDER_NOT_FOUND(1502, "Health Service Order Not Found"),
    HEALTH_SERVICE_ORDER_IS_EXISTED(1503, "Health Service Order Existed"),

    //Package
    PACKAGE_NOT_FOUND(1504, "Package Not Found"),

    //Dashboard
    USER_NOT_ADMIN(1505, "User Not Admin"),
    USER_NOT_CUSTOMER(1506, "User Not Customer"),


    CHECKING_KOI_HEALTH_NOT_FOUND(1507, "Checking Koi Health Not Found"),
    INTERNAL_SEVER_ERROR(1600, "Internal Sever Error"),
    ROLE_NOT_EXISTED(1601, "Role Not Existed"),
    NOT_ENOUGH_CHECKING_KOI_HEALTH(1602, "Not Enough Checking Koi Health"),
    FEEDBACK_ORDER_COMPLETED(1603, "Only feedback order is completed"),
    NEED_DELIVERY_HISTORY(1604, "You need to create delivery history before complete handover document"),
    PACK_ORDER_BEFORE(1605, "Package status must be PACKED before complete ORDER"),
    REPORT_NOT_FOUND(1606, "Report not Found")
    ;

    private final int code;
    private final String message;
}