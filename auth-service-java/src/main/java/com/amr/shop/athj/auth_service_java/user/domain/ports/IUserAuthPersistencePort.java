package com.amr.shop.athj.auth_service_java.user.domain.ports;

import com.amr.shop.athj.auth_service_java.user.domain.UserAuthModel;

public interface IUserAuthPersistencePort {
  void save(UserAuthModel userAuthModel);

  void update(UserAuthModel userAuthModel);
}
