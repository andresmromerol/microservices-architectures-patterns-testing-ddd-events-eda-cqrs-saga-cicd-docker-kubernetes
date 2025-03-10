package com.amr.shop.cmmj.common_java_context.shared.abstracts;

import com.amr.shop.cmmj.common_java_context.shared.event.DomainEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot<ID> extends Entity<ID> {
  private List<DomainEvent> domainEvents = new ArrayList<>();

  public final List<DomainEvent> pullDomainEvents() {
    List<DomainEvent> events = domainEvents;

    domainEvents = Collections.emptyList();

    return events;
  }

  protected final void record(DomainEvent event) {
    domainEvents.add(event);
  }
}
