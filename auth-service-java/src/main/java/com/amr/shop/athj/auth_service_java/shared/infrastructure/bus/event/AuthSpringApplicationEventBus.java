package com.amr.shop.athj.auth_service_java.shared.infrastructure.bus.event;

import com.amr.shop.cmmj.common_java_context.shared.event.DomainEvent;
import com.amr.shop.cmmj.common_java_context.shared.event.IEventBus;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service("local")
public class AuthSpringApplicationEventBus implements IEventBus {
  private final ApplicationEventPublisher publisher;

  public AuthSpringApplicationEventBus(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public void publish(final List<DomainEvent> events) {
    events.forEach(this::publish);
  }

  public void publish(final DomainEvent event) {
    this.publisher.publishEvent(event);
  }
}
