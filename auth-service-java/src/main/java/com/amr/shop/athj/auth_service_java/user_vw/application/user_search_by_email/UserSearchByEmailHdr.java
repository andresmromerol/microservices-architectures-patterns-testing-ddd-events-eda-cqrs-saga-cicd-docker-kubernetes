package com.amr.shop.athj.auth_service_java.user_vw.application.user_search_by_email;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSearchByEmailHdr
    implements IQueryHandler<UserSearchByEmailQry, UserSearchByEmailRes> {
  private final UserSearchByEmail userSearchByEmail;

  @Autowired
  public UserSearchByEmailHdr(UserSearchByEmail userSearchByEmail) {
    this.userSearchByEmail = userSearchByEmail;
  }

  @Override
  public UserSearchByEmailRes handle(UserSearchByEmailQry query) {
    return userSearchByEmail.execute(query.email());
  }
}
