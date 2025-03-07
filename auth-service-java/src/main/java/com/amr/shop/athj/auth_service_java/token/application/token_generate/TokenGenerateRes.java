package com.amr.shop.athj.auth_service_java.token.application.token_generate;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IResponse;

public record TokenGenerateRes(String tokenGenerated) implements IResponse {}
