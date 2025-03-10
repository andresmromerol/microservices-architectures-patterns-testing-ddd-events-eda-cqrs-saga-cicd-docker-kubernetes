package com.amr.shop.usr.user_context.user.application.create_user.customer;

import com.amr.shop.cmmj.common_java_context.services.auth.event.CustomerRegisteredEvent;
import com.amr.shop.cmmj.common_java_context.shared.event.DomainEventSubscriber;
import com.amr.shop.usr.user_context.user.application.create_user.UserCreate;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@DomainEventSubscriber({CustomerRegisteredEvent.class})
@Slf4j
public class CreateUserCustomerOnAuthRegistered {
  private final UserCreate userCreate;

  @Autowired
  public CreateUserCustomerOnAuthRegistered(UserCreate userCreate) {
    this.userCreate = userCreate;
  }

  @EventListener
  public void on(CustomerRegisteredEvent event) {
    log.info("creating customer user with id: {}", event.getAggregateId());
    userCreate.executeCustomer(
        UUID.fromString(event.getAggregateId()),
        event.getName(),
        event.getEmail(),
        event.getPhone(),
        event.getAddress());
  }
}
