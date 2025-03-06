package com.amr.shop.athj.auth_service_java.user.domain;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import com.amr.shop.cmmj.common_java_context.services.user.vo.*;
import com.amr.shop.cmmj.common_java_context.shared.abstracts.AggregateRoot;
import java.util.Set;
import java.util.UUID;

public class UserModel extends AggregateRoot<UserId> {

    private final NameVo name;
    private final EmailVo email;
    private final PasswordVo password;
    private final UserStatus status;
    private final PhoneVo phone;
    private final Set<RoleEnum> roles;

    public UserModel(
            UserId authId,
            NameVo name,
            EmailVo email,
            PasswordVo password,
            UserStatus status,
            PhoneVo phone,
            Set<RoleEnum> roles) {
        super.setId(authId);
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.phone = phone;
        this.roles = roles;
    }

    public static UserModel create(
            UUID id,
            String name,
            String email,
            String password,
            UserStatusEnum status,
            String phone,
            Set<RoleEnum> roles) {

        return new UserModel(
                new UserId(id),
                new NameVo(name),
                new EmailVo(email),
                new PasswordVo(password),
                new UserStatus(status),
                new PhoneVo(phone),
                roles);
    }

    public NameVo getName() {
        return name;
    }

    public EmailVo getEmail() {
        return email;
    }

    public PasswordVo getPassword() {
        return password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public PhoneVo getPhone() {
        return phone;
    }

    public Set<RoleEnum> getRoles() {
        return roles;
    }
}
