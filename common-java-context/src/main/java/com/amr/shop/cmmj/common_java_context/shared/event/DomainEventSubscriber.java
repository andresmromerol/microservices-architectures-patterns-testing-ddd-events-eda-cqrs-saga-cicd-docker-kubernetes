package com.amr.shop.cmmj.common_java_context.shared.event;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface DomainEventSubscriber {
  Class<? extends DomainEvent>[] value();
}
