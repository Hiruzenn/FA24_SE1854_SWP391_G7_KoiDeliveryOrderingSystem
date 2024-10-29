//package com.swp391.group7.KoiDeliveryOrderingSystem.controller;
//
//import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
//import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CustomerDTO;
//import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
//import com.swp391.group7.KoiDeliveryOrderingSystem.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/customers") // Base URL for the certificate endpoints
//public class CustomerController {
//    @Autowired
//    private CustomerService customerService;
//    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse<CustomerDTO>> updateCustomer(
//            @PathVariable int id,
//            @RequestBody CustomerDTO customerDTO) {
//        try {
//            // Call the service to update the customer
//            CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
//
//            // Build the success response
//            ApiResponse<CustomerDTO> response = ApiResponse.<CustomerDTO>builder()
//                    .code(HttpStatus.OK.value())
//                    .message("Customer updated successfully")
//                    .result(updatedCustomer)
//                    .build();
//
//            return ResponseEntity.ok(response); // Return HTTP 200 OK
//        } catch (AppException e) {
//            // Handle specific exceptions like customer not found
//            ApiResponse<CustomerDTO> response = ApiResponse.<CustomerDTO>builder()
//                    .code(e.getErrorCode().getCode())
//                    .message(e.getMessage())
//                    .result(null)
//                    .build();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
//        } catch (Exception e) {
//            // Handle general exceptions
//            ApiResponse<CustomerDTO> response = ApiResponse.<CustomerDTO>builder()
//                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .message("An unexpected error occurred")
//                    .result(null)
//                    .build();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
//        }
//    }
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable int id) {
//        try {
//            // Call the service to delete the customer
//            customerService.deleteCustomer(id);
//
//            // Build the success response
//            ApiResponse<Void> response = ApiResponse.<Void>builder()
//                    .code(HttpStatus.OK.value())
//                    .message("Customer deleted successfully")
//                    .result(null)
//                    .build();
//
//            return ResponseEntity.ok(response); // Return HTTP 200 OK
//        } catch (AppException e) {
//            // Handle specific exceptions like customer not found
//            ApiResponse<Void> response = ApiResponse.<Void>builder()
//                    .code(e.getErrorCode().getCode())
//                    .message(e.getMessage())
//                    .result(null)
//                    .build();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Return HTTP 400 Bad Request
//        } catch (Exception e) {
//            // Handle general exceptions
//            ApiResponse<Void> response = ApiResponse.<Void>builder()
//                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                    .message("An unexpected error occurred")
//                    .result(null)
//                    .build();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
//        }
//    }
//}
