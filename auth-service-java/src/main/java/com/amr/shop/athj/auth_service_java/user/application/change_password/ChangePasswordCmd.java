package com.amr.shop.athj.auth_service_java.user.application.change_password;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommand;

public record ChangePasswordCmd(
    String email, String currentPassword, String newPassword, String confirmationPassword)
    implements ICommand {}
