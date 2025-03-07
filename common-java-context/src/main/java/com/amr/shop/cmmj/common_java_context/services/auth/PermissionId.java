package com.amr.shop.cmmj.common_java_context.services.auth;

import com.amr.shop.cmmj.common_java_context.shared.abstracts.Id;
import java.util.UUID;

public class PermissionId extends Id<UUID> {
    protected PermissionId(UUID value) {
        super(value);
    }
}
