package com.swp391.group7.KoiDeliveryOrderingSystem.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ApiResponse <T> {
    public int code;
    public String message;
    public T result;
}