package com.amr.shop.athj.auth_service_java.user.application.encrypt_password;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IResponse;

public record EncryptPasswordRes(String encryptedPassword) implements IResponse {}
