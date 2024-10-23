package com.swp391.group7.KoiDeliveryOrderingSystem.service;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.AppException;
import com.swp391.group7.KoiDeliveryOrderingSystem.exception.ErrorCode;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.dto.CustomerDTO;
import com.swp391.group7.KoiDeliveryOrderingSystem.payload.response.CustomerRespone;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CustomerDTO> getListCertificate() {

        List<Users> usersList = userRepository.findAll();
        if (usersList.isEmpty()) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }
        return usersList.stream().map(customers -> modelMapper.map(customers, CustomerDTO.class)).toList();
    }

    public CustomerDTO getCustomerById(int id) {
        // Fetch the customer by ID from the repository
        Users existingCustomer = userRepository.findById(id);
        if (existingCustomer == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }

        // Map the retrieved customer entity to CustomerDTO and return it
        return modelMapper.map(existingCustomer, CustomerDTO.class);
    }

    public CustomerDTO updateCustomer(int id, CustomerDTO customerDTO) {
        // Fetch the existing customer by ID
        Users existingCustomer = userRepository.findById(id);
        if (existingCustomer == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }

        // Update the fields of the existing customer using values from customerDTO
        modelMapper.map(customerDTO, existingCustomer);

        // Save the updated customer back to the repository
        existingCustomer = userRepository.save(existingCustomer);

        // Map the saved customer entity to CustomerDTO and return it
        return modelMapper.map(existingCustomer, CustomerDTO.class);
    }

    public void deleteCustomer(int id) {
        // Check if the customer exists before deletion
        Users existingCustomer = userRepository.findById(id);
        if (existingCustomer == null) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_EXISTED);
        }

        // Delete the customer from the repository
        userRepository.deleteById(id);
    }


}
