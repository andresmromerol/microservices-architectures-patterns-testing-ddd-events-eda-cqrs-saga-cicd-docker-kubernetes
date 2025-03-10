package com.amr.shop.cmmj.common_java_context.shared.event;

import java.util.List;

public interface IEventBus {
  void publish(final List<DomainEvent> events);

  void publish(final DomainEvent event);
}
