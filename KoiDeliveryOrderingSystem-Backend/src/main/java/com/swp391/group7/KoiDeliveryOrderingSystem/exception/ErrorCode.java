package com.swp391.group7.KoiDeliveryOrderingSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_REQUEST(1000, "Invalid request"),
    ACCOUNT_REGISTERED(1001, "User already registered"),
    INVALID_PASSWORD(1002, "Invalid password"),
    USER_NOT_EXISTED(1003, "User not found"),
    INVALID_REPEAT_PASSWORD(1004, "Invalid repeat password"),
    UNAUTHENTICATED(1005, "Unauthenticated"),
    NOT_LOGIN(1006, "Not logged in"),
    INVALID_TOKEN(1007, "Invalid token"),
    UNVERIFIED_ACCOUNT(1008, "Unverified account"),
    ORDER_NOT_FOUND(1010, "Order not found"),
    ORDER_DETAIL_NOT_FOUND(1011, "Order detail not found"),
    FISH_PROFILE_NOT_FOUND(1012, "Fish profile not found"),
    CERTIFICATE_NOT_FOUND(1013, "Certificate not found"),
    CERTIFICATE_EXISTED(1014, "Certificate already exists"),
    HEALTH_SERVICE_CATEGORY_NOT_FOUND(1015, "Health service category not found"),
    HEALTH_SERVICE_ORDER_NOT_FOUND(1016, "Health service order not found"),
    HEALTH_SERVICE_ORDER_IS_EXISTED(1017, "Health service order already exists"),
    NOT_ENOUGH_CHECKING_KOI_HEALTH(1018, "Not enough checking Koi health records"),
    CHECKING_KOI_HEALTH_NOT_FOUND(1019, "Checking Koi health record not found"),
    USER_NOT_ADMIN(1020, "User is not an admin"),
    USER_NOT_CUSTOMER(1021, "User is not a customer"),
    DELIVERY_METHOD_NOT_FOUND(1022, "Delivery method not found"),
    NEED_DELIVERY_HISTORY(1023, "Delivery history required before completing handover document"),
    PACKAGE_NOT_FOUND(1024, "Package not found"),
    PACK_ORDER_BEFORE(1025, "Package status must be PACKED before completing order"),
    REQUIRED_HANDOVER_DOCUMENT(1026, "Handover document required when creating package"),
    REPORT_NOT_FOUND(1027, "Report not found"),
    DELETE_HANDOVER_PENDING(1028, "Only delete handover documents with pending status"),
    HANDOVER_DOCUMENT_NOT_FOUND(1029, "Handover document not found"),
    CUSTOM_DECLARATION_NOT_EXISTED(1030, "Customs declaration not found"),
    INVOICE_NOT_FOUND(1031, "Invoice not found"),
    HEALTHCARE_DELIVERY_HISTORY_NOT_FOUND(1032, "Healthcare delivery history not found"),
    FISH_CATEGORY_NOT_FOUND(1033, "Fish category not found"),
    ROLE_NOT_EXISTED(1034, "Role not found"),
    INTERNAL_SERVER_ERROR(1035, "Internal server error"),
    FEEDBACK_ORDER_COMPLETED(1036, "Feedback only allowed for completed orders"),
    NOT_DELETE_PACKED(1037, "Can't deleted package with status packed"),
    PACKAGE_FISH_HEALTHY(1038, "Only create package when all fish is HEALTHY"),
    DELIVERY_METHOD_IN_USE(1039, "Delivery method in use"),
    HEALTH_SERVICE_CATEGORY_IN_USE(1040, "Health Service Category in user")
    ;

    private final int code;
    private final String message;
}