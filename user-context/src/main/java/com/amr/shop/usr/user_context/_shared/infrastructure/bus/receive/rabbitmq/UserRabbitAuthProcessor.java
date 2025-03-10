package com.amr.shop.usr.user_context._shared.infrastructure.bus.receive.rabbitmq;

import com.amr.shop.cmmj.common_java_context.services.auth.event.AdministratorRegisteredEvent;
import com.amr.shop.cmmj.common_java_context.services.auth.event.CustomerRegisteredEvent;
import com.amr.shop.cmmj.common_java_context.shared.event.IEventBusEventDistributor;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRabbitAuthProcessor {
  private IEventBusEventDistributor eventBusEventConfiguration;

  @Autowired
  public UserRabbitAuthProcessor(IEventBusEventDistributor eventBusEventConfiguration) {
    this.eventBusEventConfiguration = eventBusEventConfiguration;
  }

  @Bean
  public Consumer<AdministratorRegisteredEvent> createUserAdministratorOnUserAuthRegistered() {
    return event -> {
      log.info("ADMIN event: UserId={}, UserName={}", event.getEmail(), event.getAggregateId());
      eventBusEventConfiguration.publish(event);
    };
  }

  @Bean
  public Consumer<CustomerRegisteredEvent> createUserCustomerOnUserAuthRegistered() {
    return event -> {
      log.info("CUSTOMER event: UserId={}, UserName={}", event.getEmail(), event.getAggregateId());
      eventBusEventConfiguration.publish(event);
    };
  }
}
