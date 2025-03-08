package com.amr.shop.usr.user_context.user.domain;

import com.amr.shop.cmmj.common_java_context.services.user.UserStatusEnum;
import com.amr.shop.cmmj.common_java_context.services.user.id.UserId;
import com.amr.shop.cmmj.common_java_context.services.user.vo.EmailVo;
import com.amr.shop.cmmj.common_java_context.services.user.vo.NameVo;
import com.amr.shop.cmmj.common_java_context.services.user.vo.PhoneVo;
import com.amr.shop.cmmj.common_java_context.services.user.vo.UserStatus;
import com.amr.shop.cmmj.common_java_context.shared.abstracts.AggregateRoot;
import java.util.UUID;

public abstract class UserModel extends AggregateRoot<UserId> {
  private final NameVo name;
  private final EmailVo email;
  private final UserStatus status;
  private final PhoneVo phone;

  protected UserModel(UUID id, String name, String email, UserStatusEnum status, String phone) {
    super.setId(new UserId(id));
    this.name = new NameVo(name);
    this.email = new EmailVo(email);
    this.status = new UserStatus(status);
    this.phone = new PhoneVo(phone);
  }

  public abstract UserModelDto toPrimitives();

  public abstract boolean isNullModel();

  public abstract void accept(IUserVisitor visitor);

  public String getName() {
    return name.getName();
  }

  public String getEmail() {
    return email.getValue();
  }

  public UserStatusEnum getStatus() {
    return status.getValue();
  }

  public String getPhone() {
    return phone.getValue();
  }

  public UUID getAggId() {
    return super.getId().getValue();
  }
}
