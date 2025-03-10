package com.amr.shop.cmmj.common_java_context.shared.event;

import com.amr.shop.cmmj.common_java_context.shared.util.Util;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class DomainEvent {
  private String aggregateId;
  private String eventId;
  private String occurredOn;

  public DomainEvent(String aggregateId) {
    this.aggregateId = aggregateId;
    this.eventId = UUID.randomUUID().toString();
    this.occurredOn = Util.dateToString(LocalDateTime.now());
  }

  public DomainEvent(String aggregateId, String eventId, String occurredOn) {
    this.aggregateId = aggregateId;
    this.eventId = eventId;
    this.occurredOn = occurredOn;
  }

  protected DomainEvent() {}

  public abstract String eventName();

  public String getAggregateId() {
    return aggregateId;
  }

  public void setAggregateId(String aggregateId) {
    this.aggregateId = aggregateId;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public String getOccurredOn() {
    return occurredOn;
  }

  public void setOccurredOn(String occurredOn) {
    this.occurredOn = occurredOn;
  }
}
