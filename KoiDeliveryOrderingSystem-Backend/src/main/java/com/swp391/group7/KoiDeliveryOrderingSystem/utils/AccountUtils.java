package com.swp391.group7.KoiDeliveryOrderingSystem.utils;

import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Users;
import com.swp391.group7.KoiDeliveryOrderingSystem.entity.Role;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.UserRepository;
import com.swp391.group7.KoiDeliveryOrderingSystem.repository.RoleRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AccountUtils {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AccountUtils(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public Users getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            String userId = jwt.getSubject();
            String roleName = jwt.getClaim("role");
            Role role = roleRepository.findByName(roleName);
            return userRepository.findByIdAndRole(Integer.parseInt(userId), role);
        } else {
            return null;
        }
    }
    
}
