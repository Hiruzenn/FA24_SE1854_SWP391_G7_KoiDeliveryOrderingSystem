package com.swp391.group7.KoiDeliveryOrderingSystem.utils;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Customers;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Role;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.CustomersRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.RoleRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UsersRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AccountUtils {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final CustomersRepository customersRepository;

    public AccountUtils(UsersRepository usersRepository, RoleRepository roleRepository, CustomersRepository customersRepository) {
        this.usersRepository = usersRepository;
        this.roleRepository = roleRepository;
        this.customersRepository = customersRepository;
    }

    public Users getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            String userId = jwt.getSubject();
            String roleName = jwt.getClaim("role");
            Role role = roleRepository.findByName(roleName);
            return usersRepository.findByIdAndRole(Integer.parseInt(userId), role);
        } else {
            return null;
        }
    }

    public Customers getCurrentCustomer() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            String customerId = jwt.getSubject();
            return customersRepository.findById(Integer.parseInt(customerId));
        }else{
            return null;
        }
    }
}
