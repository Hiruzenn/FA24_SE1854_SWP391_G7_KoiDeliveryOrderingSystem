package com.swp391.group7.KoiDeliveryOrderingSystem.controller;

import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages.CreatePackageRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.request.packages.UpdatePackageRequest;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.ApiResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.PackageResponse;
import com.swp391.group7.KoiDeliveryOrderingSystem.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("package") // Base URL for the Package endpoints
public class PackageController {
    @Autowired
    private PackageService packageService;

    @PostMapping("create")
    public ApiResponse<PackageResponse> createPackage(@RequestBody CreatePackageRequest createPackageRequest){
        var result = packageService.createPackage(createPackageRequest);
        return ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package Created")
                .result(result)
                .build();
    }

    @PutMapping("update/{packageId}")
    public ApiResponse<PackageResponse> updatePackage(@PathVariable Integer packageId, @RequestBody UpdatePackageRequest updatePackageRequest){
        var result = packageService.updatePackage(packageId, updatePackageRequest);
        return ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package Created")
                .result(result)
                .build();
    }

    @GetMapping("view-all")
    public ApiResponse<List<PackageResponse>> viewAllPackage(){
        var result = packageService.getAllPackages();
        return ApiResponse.<List<PackageResponse>>builder()
                .code(200)
                .message("View All")
                .result(result)
                .build();
    }

    @GetMapping("view-by-packageno/{packageNo}")
    public ApiResponse<PackageResponse> viewPackageByPackageNo(@PathVariable String packageNo){
        var result = packageService.getPackageByPackageNo(packageNo);
        return  ApiResponse.<PackageResponse>builder()
                .code(200)
                .message("Package No Found")
                .result(result)
                .build();
    }
}
