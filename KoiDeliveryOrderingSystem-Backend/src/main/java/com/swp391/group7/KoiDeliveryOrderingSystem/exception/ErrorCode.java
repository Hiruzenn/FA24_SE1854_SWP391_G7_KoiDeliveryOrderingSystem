package com.swp391.group7.KoiDeliveryOrderingSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(1001,"User existed"),
    USER_NOT_EXISTED(1002,"User Not Found"),
    CUSTOMER_NOT_EXISTED(1003,"Customer Not Found"),
    UNAUTHENTICATED(1004,"Unauthenticated"),
    ;

    private final int code;
    private final String message;
}
