package com.amr.shop.usr.user_context.user.application.create_user.administrator;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommand;
import java.util.UUID;

public record UserCreateAdministratorCmd(
    UUID id, String name, String email, UUID createdByAdminId, String phone) implements ICommand {}
