package com.amr.shop.athj.auth_service_java.shared.infrastructure.bus.event;

import com.amr.shop.cmmj.common_java_context.shared.event.DomainEvent;
import com.amr.shop.cmmj.common_java_context.shared.event.IEventBus;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service("rabbitmq")
@Slf4j
public class AuthRabbitMQEventBus implements IEventBus {
  private StreamBridge streamBridge;

  @Autowired
  public AuthRabbitMQEventBus(StreamBridge streamBridge) {
    this.streamBridge = streamBridge;
  }

  @Override
  public void publish(List<DomainEvent> events) {
    events.forEach(this::publish);
  }

  public void publish(final DomainEvent event) {
    boolean sent = streamBridge.send(event.eventName(), event);
    if (sent) {
      log.info("Event sent successfully.{}", event.eventName());
    } else {
      log.info("Event not sent.{}", event.eventName());
    }
  }
}
