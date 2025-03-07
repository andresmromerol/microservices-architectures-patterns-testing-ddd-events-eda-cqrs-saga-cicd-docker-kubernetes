package com.amr.shop.athj.auth_service_java.user.application.validate_token;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommand;

public record ValidateTokenCmd(String token) implements ICommand {}
