package com.swp391.group7.KoiDeliveryOrderingSystem.repository;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Enum.CustomerStatusEnum;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Role;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    Users findById(int id);
    Users findByIdAndRole(int id, Role role);
    Optional<Users> findByEmailAndCustomerStatus(String username, CustomerStatusEnum status);
    Optional<Users> findByIdAndCustomerStatus(Integer id, CustomerStatusEnum status);
}
