package com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email;

import com.amr.shop.cmmj.common_java_context.services.auth.PermissionEnum;
import com.amr.shop.cmmj.common_java_context.services.auth.RoleEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IResponse;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserSearchByEmailRes(
        UUID id,
        String name,
        String email,
        String password,
        String phone,
        Set<RoleEnum> roles,
        Set<PermissionEnum> permissions,
        boolean isEmpty)
        implements IResponse {}
