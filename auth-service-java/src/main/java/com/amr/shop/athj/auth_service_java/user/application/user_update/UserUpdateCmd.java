package com.amr.shop.athj.auth_service_java.user.application.user_update;

import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommand;
import java.util.Set;
import java.util.UUID;

public record UserUpdateCmd(
    UUID id,
    String name,
    String email,
    String password,
    String phone,
    Set<RoleEnum> roles,
    UserStatusEnum status)
    implements ICommand {}
