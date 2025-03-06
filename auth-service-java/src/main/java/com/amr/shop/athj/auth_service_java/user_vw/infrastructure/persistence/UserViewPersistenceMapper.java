package com.amr.shop.athj.auth_service_java.user_vw.infrastructure.persistence;

import com.amr.shop.athj.auth_service_java.shared.infrastructure.jpa.user.UserJpa;
import com.amr.shop.athj.auth_service_java.user_vw.domain.UserView;
import com.amr.shop.cmmj.common_java_context.services.auth.PermissionEnum;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class UserViewPersistenceMapper {

    private static Set<PermissionEnum> getPermissions(UserJpa userJpa) {
        return userJpa.getRoles().stream()
                .flatMap(role -> Stream.of(role.getPermissions()))
                .collect(Collectors.toSet());
    }

    public UserView jpaToModel(UserJpa userJpa) {

        return new UserView(
                userJpa.getId(),
                userJpa.getName(),
                userJpa.getEmail(),
                userJpa.getPassword(),
                userJpa.getPhone(),
                userJpa.getRoles(),
                getPermissions(userJpa));
    }
}
