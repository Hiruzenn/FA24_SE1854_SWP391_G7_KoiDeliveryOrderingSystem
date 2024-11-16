package com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.handovedocument;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.HandoverStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Orders;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHandoverDocumentRequest {
    @NotBlank(message = "Handover Document Description is required")
    private String handoverDescription;

    @NotBlank(message = "You need to upload image to complete Order")
    private String image;
}
