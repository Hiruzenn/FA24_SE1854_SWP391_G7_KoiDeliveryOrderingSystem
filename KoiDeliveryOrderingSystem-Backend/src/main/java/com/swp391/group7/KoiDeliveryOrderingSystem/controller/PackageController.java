package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages.CreatePackageRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages.UpdatePackageRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PackageResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.PackageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("package") // Base URL for the Package endpoints
public class PackageController {
    @Autowired
    private PackageService packageService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse<PackageResponse>> createPackage(@Valid @RequestBody CreatePackageRequest createPackageRequest){
        var result = packageService.createPackage(createPackageRequest);
        return ResponseEntity.ok(ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package Created")
                .result(result)
                .build());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<PackageResponse>> updatePackage(@Valid @PathVariable Integer id, @RequestBody UpdatePackageRequest updatePackageRequest){
        var result = packageService.updatePackage(id, updatePackageRequest);
        return ResponseEntity.ok(ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package Updated")
                .result(result)
                .build());
    }

    @GetMapping("view-all")
    public ResponseEntity<ApiResponse<List<PackageResponse>>> viewAllPackage(){
        var result = packageService.getAllPackages();
        return ResponseEntity.ok(ApiResponse.<List<PackageResponse>>builder()
                .code(200)
                .message("View All")
                .result(result)
                .build());
    }

    @GetMapping("view-by-packageNo/{packageNo}")
    public ResponseEntity<ApiResponse<PackageResponse>> viewPackageByPackageNo(@PathVariable String packageNo){
        var result = packageService.getPackageByPackageNo(packageNo);
        return  ResponseEntity.ok(ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package No Found")
                .result(result)
                .build());
    }

    @PutMapping("delete/{id}")
    public ResponseEntity<ApiResponse<PackageResponse>> deletePackage(@Valid @PathVariable Integer id){
        var result = packageService.deletePackage(id);
        return ResponseEntity.ok(ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package Deleted")
                .result(result)
                .build());
    }

}
