package com.amr.shop.athj.auth_service_java.user.application.authentication_token;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommand;

public record AuthenticationTokenCmd(String email, String password) implements ICommand {}
