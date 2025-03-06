package com.amr.shop.athj.auth_service_java.user.domain.ports;

import com.amr.shop.athj.auth_service_java.user.domain.UserModel;

public interface IUserAuthPersistencePort {
    void save(UserModel userModel);
}
