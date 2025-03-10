package com.amr.shop.athj.auth_service_java.shared.infrastructure.bus.event;

import com.amr.shop.cmmj.common_java_context.shared.event.DomainEvent;
import com.amr.shop.cmmj.common_java_context.shared.event.IEventBus;
import com.amr.shop.cmmj.common_java_context.shared.event.IEventBusEventDistributor;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AuthEventBusEventDistributor implements IEventBusEventDistributor {

  private final IEventBus localEventBus;

  private final IEventBus rabbitEventBus;

  public AuthEventBusEventDistributor(
      @Qualifier("local") IEventBus localEventBus,
      @Qualifier("rabbitmq") IEventBus rabbitEventBus) {
    this.localEventBus = localEventBus;
    this.rabbitEventBus = rabbitEventBus;
  }

  @Override
  public void publish(List<DomainEvent> domainEvents) {

    localEventBus.publish(domainEvents);
    rabbitEventBus.publish(domainEvents);
  }

  @Override
  public void publish(DomainEvent domainEvent) {
    localEventBus.publish(domainEvent);
  }
}
