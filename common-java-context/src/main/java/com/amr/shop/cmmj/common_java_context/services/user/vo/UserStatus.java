package com.amr.shop.cmmj.common_java_context.services.user.vo;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;

public class UserStatus {
    private UserStatusEnum status;

    public UserStatus(UserStatusEnum status) {
        this.status = status;
    }

    public UserStatusEnum getValue() {
        return status;
    }
}
