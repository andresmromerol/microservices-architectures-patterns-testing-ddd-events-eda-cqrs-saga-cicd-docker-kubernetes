package com.amr.shop.usr.user_context.user.domain;

import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import java.util.Optional;

public interface IUserPersistencePort {
  void save(UserModel user);

  Optional<UserModel> findByEmail(EmailVo email);
}
