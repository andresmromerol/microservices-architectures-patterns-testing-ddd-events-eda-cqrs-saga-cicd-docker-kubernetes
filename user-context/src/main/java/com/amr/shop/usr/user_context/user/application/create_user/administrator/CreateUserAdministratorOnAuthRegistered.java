package com.amr.shop.usr.user_context.user.application.create_user.administrator;

import com.amr.shop.cmmj.common_java_context.services.auth.event.AdministratorRegisteredEvent;
import com.amr.shop.cmmj.common_java_context.shared.event.DomainEventSubscriber;
import com.amr.shop.usr.user_context.user.application.create_user.UserCreate;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@DomainEventSubscriber({AdministratorRegisteredEvent.class})
@Slf4j
public class CreateUserAdministratorOnAuthRegistered {
  private final UserCreate userCreate;

  @Autowired
  public CreateUserAdministratorOnAuthRegistered(UserCreate userCreate) {
    this.userCreate = userCreate;
  }

  @EventListener
  public void on(AdministratorRegisteredEvent event) {
    log.info("Administrator with id {} created", event.getAggregateId());
    userCreate.executeAdministrator(
        UUID.fromString(event.getAggregateId()),
        event.getName(),
        event.getEmail(),
        event.getPhone(),
        UUID.fromString(event.getCreatedByAdminId()));
  }
}
