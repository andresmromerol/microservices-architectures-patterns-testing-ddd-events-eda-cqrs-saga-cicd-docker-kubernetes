package com.amr.shop.usr.user_context.user.application.search_by_id;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQuery;

public record UserSearchByEmailQry(String email) implements IQuery {}
