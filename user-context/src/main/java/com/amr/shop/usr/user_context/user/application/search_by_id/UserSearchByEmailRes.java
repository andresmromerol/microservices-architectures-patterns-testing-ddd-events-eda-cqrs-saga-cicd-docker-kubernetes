package com.amr.shop.usr.user_context.user.application.search_by_id;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IResponse;
import java.util.UUID;

public record UserSearchByEmailRes(
    UUID id, String name, String email, UserStatusEnum status, String phone, boolean isEmpty)
    implements IResponse {}
