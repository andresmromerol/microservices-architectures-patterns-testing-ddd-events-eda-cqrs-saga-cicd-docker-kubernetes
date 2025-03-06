package com.amr.shop.cmmj.common_java_context.services.user.id;

import com.amr.shop.cmmj.common_java_context.shared.abstracts.Id;
import java.util.UUID;

public class UserId extends Id<UUID> {

    public UserId(UUID value) {

        super(value);
    }
}
