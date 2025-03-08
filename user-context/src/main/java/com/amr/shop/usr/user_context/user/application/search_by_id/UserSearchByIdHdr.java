package com.amr.shop.usr.user_context.user.application.search_by_id;

import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSearchByIdHdr
    implements IQueryHandler<UserSearchByEmailQry, UserSearchByEmailRes> {
  private final UserSearchById userSearchById;

  @Autowired
  public UserSearchByIdHdr(UserSearchById userSearchById) {
    this.userSearchById = userSearchById;
  }

  @Override
  public UserSearchByEmailRes handle(UserSearchByEmailQry query) {
    return userSearchById.execute(query.email());
  }
}
