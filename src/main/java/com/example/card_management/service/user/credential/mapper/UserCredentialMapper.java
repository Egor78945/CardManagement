package com.example.card_management.service.user.credential.mapper;

import com.example.card_management.enumeration.role.RoleType;
import com.example.card_management.model.role.entity.Role;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.model.user.role.entity.UserRole;
import com.example.card_management.model.user.security.UserCredentialDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserCredentialMapper {
    private final PasswordEncoder passwordEncoder;

    public UserCredentialMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserCredential mapTo(UserCredentialDTO user) {
        UserCredential userCredential = new UserCredential(user.getEmail(), passwordEncoder.encode(user.getPassword()));
        userCredential.setRoleList(Arrays.asList(new UserRole(userCredential, new Role(RoleType.ROLE_USER.getId(), RoleType.ROLE_USER.name()))));
        return userCredential;
    }
}
