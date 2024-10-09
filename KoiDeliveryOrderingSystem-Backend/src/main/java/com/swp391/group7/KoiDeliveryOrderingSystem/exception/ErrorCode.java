package com.swp391.group7.KoiDeliveryOrderingSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //AUTH
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
    ;
    private final int code;
    private final String message;
}
