package com.amr.shop.athj.auth_service_java.user_vw.domain;

import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import java.util.Optional;

public interface IUserViewPersistencePort {
  Optional<UserView> search(EmailVo email);
}
