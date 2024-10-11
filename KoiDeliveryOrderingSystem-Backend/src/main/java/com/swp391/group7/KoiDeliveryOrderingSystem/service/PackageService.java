package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Package;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.PackageDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.PackageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<PackageDTO> getListPackage()
    {

        List <Package> packageList= packageRepository.findAll();
        if (packageList.isEmpty()){
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        return packageList.stream().map( Package -> modelMapper.map(Package, PackageDTO.class)).toList();
    }

    public PackageDTO getPackageId(long id) {
        // Fetch the customer by ID from the repository
        Package package1 = packageRepository.findById(id).
                orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_EXISTED));


        // Map the retrieved customer entity to CustomerDTO and return it
        return modelMapper.map( package1, PackageDTO.class);
    }
    public PackageDTO updatePackage(long id, PackageDTO packageDTO) {
        // Fetch the existing package by ID
        Package existingPackage = packageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_EXISTED));

        // Update the fields of the existing package using values from packageDTO
        modelMapper.map(packageDTO, existingPackage);

        // Save the updated package back to the repository
        existingPackage = packageRepository.save(existingPackage);

        // Map the saved package entity to PackageDTO and return it
        return modelMapper.map(existingPackage, PackageDTO.class);
    }
    public void deletePackage(long id) {
        // Check if the package exists before deletion
        Package existingPackage = packageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PACKAGE_NOT_EXISTED));

        // Delete the package from the repository
        packageRepository.delete(existingPackage);
    }

    public PackageDTO createPackage(PackageDTO packageDTO) {
        // Map the incoming DTO to the Package entity
        Package newPackage = modelMapper.map(packageDTO, Package.class);

        // Save the package to the repository
        newPackage = packageRepository.save(newPackage);

        // Convert the saved entity back to a DTO and return it
        return modelMapper.map(newPackage, PackageDTO.class);
    }
}
