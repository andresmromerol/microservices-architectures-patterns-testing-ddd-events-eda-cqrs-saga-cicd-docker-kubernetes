package com.amr.shop.athj.auth_service_java.token.application.token_refresh;

import com.amr.shop.cmmj.common_java_context.services.auth.BuildTokenDto;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQuery;

public record TokenRefreshQry(BuildTokenDto token, String secretKey) implements IQuery {}
