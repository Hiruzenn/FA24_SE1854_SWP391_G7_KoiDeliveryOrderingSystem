package com.swp391.group7.KoiDeliveryOrderingSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //AUTH & Customers
    USER_EXISTED(1001,"User existed"),
    USER_NOT_EXISTED(1002,"User Not Found"),
    CUSTOMER_NOT_EXISTED(1003,"Customer Not Found"),
    UNAUTHENTICATED(1004,"Unauthenticated"),
    INVALID_REPEAT_PASSWORD(1005,"Invalid Re-Password"),

    //ORDER
    ORDER_NOT_FOUND(1100,"Order Not Found"),

    //ORDER_DETAIL
    ORDER_DETAIL_NOT_FOUND(1200, "Order Detail Not Found"),

    //FISH_PROFILE
    FISH_PROFILE_NOT_FOUND(1300, "Fish Profile Not Found"),

    //Certificate
    CERTIFICATE_NOT_FOUND(1400,"Certificate not found"),
    CERTIFICAT_EXISTED(1401,"Certificate existed"),

    //Package
    PACKAGE_NOT_EXISTED (1500,"Package not exsited"),

    //
    CUSTOMSDECALARATION_NOT_EXISTED(1600, "Customs Declaration not existed"),
    //
    INVOICE_NOT_FOUND(1700, "Invoice not existed"),
    //
    HANDOVER_DOCUMENT_NOT_FOUND(1800,"Handover Document not existed"),
    //
    HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND(1900, "History not existed" )
    ;
    private final int code;
    private final String message;
}