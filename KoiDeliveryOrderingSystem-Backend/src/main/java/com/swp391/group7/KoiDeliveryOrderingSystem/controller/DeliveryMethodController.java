package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.DeliveryMethod;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.DeliveryMethodDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.deliverymethod.CreateDeliveryMethodRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.DeliveryMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery-method")
@RequiredArgsConstructor
public class DeliveryMethodController {

    @Autowired
    private DeliveryMethodService deliveryMethodService;

    // Endpoint to retrieve the list of all DeliveryMethods
    @GetMapping
    public ResponseEntity<ApiResponse<List<DeliveryMethodDTO>>> getAllDeliveryMethod() {
        List<DeliveryMethodDTO> deliveryMethod = deliveryMethodService.getListDeliveryMethods();

        // Build and return the ApiResponse
        ApiResponse<List<DeliveryMethodDTO>> response = ApiResponse.<List<DeliveryMethodDTO>>builder()
                .code(200) // HTTP OK
                .message("DeliveryMethods retrieved successfully")
                .result(deliveryMethod)
                .build();

        return ResponseEntity.ok(response); // Return HTTP 200 OK
    }

    // Endpoint to retrieve an DeliveryMethod by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DeliveryMethodDTO>> getListDeliveryMethod(@PathVariable int id) {
        DeliveryMethodDTO deliveryMethodDTO = deliveryMethodService.getDeliveryMethodById(id);

        // Build and return the ApiResponse if the DeliveryMethod is found
        ApiResponse<DeliveryMethodDTO> response = ApiResponse.<DeliveryMethodDTO>builder()
                .code(200) // HTTP OK
                .message("DeliveryMethod retrieved successfully")
                .result(deliveryMethodDTO)
                .build();

        return ResponseEntity.ok(response); // Return HTTP 200 OK
    }

    // Endpoint to create a new DeliveryMethod
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DeliveryMethodDTO>> createDeliveryMethod(@RequestBody CreateDeliveryMethodRequest createDeliveryMethodRequest) {
        try {
            // Call the service to create the DeliveryMethod
            ApiResponse<DeliveryMethodDTO> createdDeliveryMethodResponse = deliveryMethodService.createDeliveryMethod(createDeliveryMethodRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDeliveryMethodResponse); // Return HTTP 201 Created
        } catch (AppException e) {
            // Handle any application exceptions
            ApiResponse<DeliveryMethodDTO> response = ApiResponse.<DeliveryMethodDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle unexpected errors
            ApiResponse<DeliveryMethodDTO> response = ApiResponse.<DeliveryMethodDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }

    // Endpoint to update an existing DeliveryMethod by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<DeliveryMethodDTO>> updateDeliveryMethod(@PathVariable Integer id, @RequestBody CreateDeliveryMethodRequest createDeliveryMethodRequest) {
        try {
            // Call the service to update the DeliveryMethod
            ApiResponse<DeliveryMethodDTO> updatedDeliveryMethodResponse = deliveryMethodService.updateDeliveryMethod(id, createDeliveryMethodRequest);
            return ResponseEntity.ok(updatedDeliveryMethodResponse); // Return HTTP 200 OK
        } catch (AppException e) {
            // Handle application-specific exceptions
            ApiResponse<DeliveryMethodDTO> response = ApiResponse.<DeliveryMethodDTO>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle unexpected exceptions
            ApiResponse<DeliveryMethodDTO> response = ApiResponse.<DeliveryMethodDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }

    // Endpoint to delete an DeliveryMethod by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDeliveryMethod(@PathVariable Integer id) {
        try {
            // Call the service to delete the DeliveryMethod
            ApiResponse<Void> deleteResponse = deliveryMethodService.deleteDeliveryMethod(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleteResponse); // Return HTTP 204 No Content
        } catch (AppException e) {
            // Handle specific exceptions like DeliveryMethod not found
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .code(e.getErrorCode().getCode())
                    .message(e.getMessage())
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
        } catch (Exception e) {
            // Handle any unexpected errors
            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }
}
