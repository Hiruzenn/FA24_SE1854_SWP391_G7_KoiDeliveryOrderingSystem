package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages.CreatePackageRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages.UpdatePackageRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PackageResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.PackageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("package") // Base URL for the Package endpoints
@Tag(name = "Package")
public class PackageController {
    @Autowired
    private PackageService packageService;

    @PostMapping("create/{orderId}")
    public ResponseEntity<ApiResponse<PackageResponse>> createPackage(@Valid @PathVariable("orderId") Integer orderId, @RequestBody CreatePackageRequest createPackageRequest) {
        var result = packageService.createPackage(orderId, createPackageRequest);
        return ResponseEntity.ok(ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package Created")
                .result(result)
                .build());
    }

    @PutMapping("update/{orderId}")
    public ResponseEntity<ApiResponse<PackageResponse>> updatePackage(@Valid @PathVariable Integer orderId, @RequestBody UpdatePackageRequest updatePackageRequest) {
        var result = packageService.updatePackage(orderId, updatePackageRequest);
        return ResponseEntity.ok(ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package Updated")
                .result(result)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<PackageResponse>>> viewAllPackage() {
        var result = packageService.getAllPackages();
        return ResponseEntity.ok(ApiResponse.<List<PackageResponse>>builder()
                .code(200)
                .message("View All")
                .result(result)
                .build());
    }

    @GetMapping("view-by-packageNo/{packageNo}")
    public ResponseEntity<ApiResponse<PackageResponse>> viewPackageByPackageNo(@PathVariable String packageNo) {
        var result = packageService.getPackageByPackageNo(packageNo);
        return ResponseEntity.ok(ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package No Found")
                .result(result)
                .build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePackage(@Valid @PathVariable Integer id) {
        packageService.deletePackage(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(200)
                .message("Package Deleted")
                .build());
    }

    @GetMapping("view-by-order/{orderId}")
    public ResponseEntity<ApiResponse<PackageResponse>> viewPackageByOrder(@Valid @PathVariable Integer orderId) {
        var result = packageService.viewPackageByOrder(orderId);
        return ResponseEntity.ok(ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package by Order")
                .result(result)
                .build());
    }
}
