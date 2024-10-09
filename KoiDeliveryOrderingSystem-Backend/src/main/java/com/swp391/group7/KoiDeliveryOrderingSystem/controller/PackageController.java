package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.PackageDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.PackageDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/package") // Base URL for the Package endpoints
public class PackageController
{
    @Autowired
    private PackageService packageService;

    @GetMapping 
    public ResponseEntity<ApiResponse<List<PackageDTO>>> getAllPackage() {
        List<PackageDTO> packageDTOListS = packageService.getListPackage();

        // Build and return the ApiResponse
        ApiResponse<List<PackageDTO>> response = ApiResponse.<List<PackageDTO>>builder()
                .code(200) 
                .message("Package retrieved successfully") 
                .result(packageDTOListS)
                .build();

        return ResponseEntity.ok(response); // Return the response with HTTP 200 OK
    }

    @GetMapping("/{id}") // Mapping for GET request to retrieve a Package by ID
    public ResponseEntity<ApiResponse<PackageDTO>> getPackageId(@PathVariable long id) {
        // Retrieve the Package by ID using the service
        PackageDTO PackageDTO = packageService.getPackageId(id);

        // Check if the Package was found
        if (PackageDTO != null) {
            // Build and return the ApiResponse with the found Package
            ApiResponse<PackageDTO> response = ApiResponse.<PackageDTO>builder()
                    .code(200)
                    .message("Package retrieved successfully")
                    .result(PackageDTO)
                    .build();
            return ResponseEntity.ok(response); // Return HTTP 200 OK
        } else {
            // Return a not found response if the Package does not exist
            ApiResponse<PackageDTO> response = ApiResponse.<PackageDTO>builder()
                    .code(404)
                    .message("Package not found")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return HTTP 404 Not Found
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<PackageDTO>> updatePackage(
            @PathVariable int id,
            @RequestBody PackageDTO packageDTO) {
        try {
            // Call the service to update the package
            PackageDTO updatedPackage = packageService.updatePackage(id, packageDTO);

            // Build the success response
            ApiResponse<PackageDTO> response = ApiResponse.<PackageDTO>builder()
                    .code(HttpStatus.OK.value())
                    .message("Package updated successfully")
                    .result(updatedPackage)
                    .build();

            return ResponseEntity.ok(response); // Return HTTP 200 OK


        } catch (Exception e) {
            // Handle general exceptions
            ApiResponse<PackageDTO> response = ApiResponse.<PackageDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PackageDTO>> createPackage(@RequestBody PackageDTO packageDTO) {
        try {
            // Call the service to create the new package
            PackageDTO createdPackage = packageService.createPackage(packageDTO);

            // Build and return the success response
            ApiResponse<PackageDTO> response = ApiResponse.<PackageDTO>builder()
                    .code(HttpStatus.CREATED.value()) // HTTP status code for created
                    .message("Package created successfully")
                    .result(createdPackage)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response); // Return HTTP 201 Created
        } catch (Exception e) {
            // Handle general exceptions
            ApiResponse<PackageDTO> response = ApiResponse.<PackageDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An unexpected error occurred")
                    .result(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Return HTTP 500 Internal Server Error
        }
    }
}
