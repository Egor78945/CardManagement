package com.example.card_management.service.user.credential.mapper;

import com.example.card_management.enumeration.user.role.type.UserRoleTypeEnumeration;
import com.example.card_management.model.user.role.entity.UserRoleType;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.model.user.role.entity.UserRole;
import com.example.card_management.model.user.credential.dto.UserCredentialDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Tag(name = "UserCredentialMapper", description = "Севрис, преобразующий основные пользовательские даннные")
@Component
public class UserCredentialMapper {
    private final PasswordEncoder passwordEncoder;

    public UserCredentialMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserCredential mapTo(UserCredentialDTO user) {
        UserCredential userCredential = new UserCredential(user.getEmail(), passwordEncoder.encode(user.getPassword()));
        userCredential.setRoleList(Arrays.asList(new UserRole(userCredential, new UserRoleType(UserRoleTypeEnumeration.ROLE_USER.getId(), UserRoleTypeEnumeration.ROLE_USER.name()))));
        return userCredential;
    }
}
