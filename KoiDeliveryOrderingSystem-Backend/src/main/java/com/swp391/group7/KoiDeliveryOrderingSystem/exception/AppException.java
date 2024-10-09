package com.swp391.group7.KoiDeliveryOrderingSystem.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException{

    public AppException(ErrorCode errorCode){
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    private ErrorCode errorCode;
}
