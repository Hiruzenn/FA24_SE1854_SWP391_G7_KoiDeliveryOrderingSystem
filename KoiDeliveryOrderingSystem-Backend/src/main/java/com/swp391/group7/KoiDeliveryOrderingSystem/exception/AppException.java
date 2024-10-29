package com.swp391.group7.KoiDeliveryOrderingSystem.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException{
        public AppException(ErrorCode errorCode) {
                super(errorCode.getMessage());
                this.errorCode = errorCode;
        }
        private ErrorCode errorCode;
}
