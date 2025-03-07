package com.amr.shop.athj.auth_service_java.token.application.token_save;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommand;
import java.util.UUID;

public record TokenSaveCmd(UUID userId, String token) implements ICommand {}
