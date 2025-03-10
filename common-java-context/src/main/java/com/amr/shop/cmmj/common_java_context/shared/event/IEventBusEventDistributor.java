package com.amr.shop.cmmj.common_java_context.shared.event;

import java.util.List;

public interface IEventBusEventDistributor {

  void publish(List<DomainEvent> domainEvents);

  void publish(DomainEvent domainEvent);
}
