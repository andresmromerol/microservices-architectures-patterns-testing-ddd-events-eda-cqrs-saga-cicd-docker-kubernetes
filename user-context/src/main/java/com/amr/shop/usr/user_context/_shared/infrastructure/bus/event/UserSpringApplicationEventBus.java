package com.amr.shop.usr.user_context._shared.infrastructure.bus.event;

import com.amr.shop.cmmj.common_java_context.shared.event.DomainEvent;
import com.amr.shop.cmmj.common_java_context.shared.event.IEventBus;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service("local")
public class UserSpringApplicationEventBus implements IEventBus {
  private final ApplicationEventPublisher publisher;

  public UserSpringApplicationEventBus(ApplicationEventPublisher publisher) {
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
