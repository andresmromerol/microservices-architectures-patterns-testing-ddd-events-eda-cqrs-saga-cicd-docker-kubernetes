package com.amr.shop.usr.user_context._shared.infrastructure.bus.query;

import com.amr.shop.cmmj.common_java_context.services.auth.CQBusEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQuery;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.IQueryHandler;
import com.amr.shop.cmmj.common_java_context.shared.bus.query.QueryNotRegisteredException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public final class UserQueryHandlersLocal {

  HashMap<Class<? extends IQuery>, Class<? extends IQueryHandler>> indexedQueryHandlers;

  public UserQueryHandlersLocal() {
    String path = CQBusEnum.USER_CONTEXT.getValue();
    Reflections reflections = new Reflections(path);
    Set<Class<? extends IQueryHandler>> classes = reflections.getSubTypesOf(IQueryHandler.class);
    indexedQueryHandlers = formatHandlers(classes);
  }

  public Class<? extends IQueryHandler> search(Class<? extends IQuery> queryClass)
      throws QueryNotRegisteredException {
    Class<? extends IQueryHandler> queryHandlerClass = indexedQueryHandlers.get(queryClass);
    if (null == queryHandlerClass) {
      throw new QueryNotRegisteredException(queryClass);
    }
    return queryHandlerClass;
  }

  private HashMap<Class<? extends IQuery>, Class<? extends IQueryHandler>> formatHandlers(
      Set<Class<? extends IQueryHandler>> queryHandlers) {
    HashMap<Class<? extends IQuery>, Class<? extends IQueryHandler>> handlers = new HashMap<>();
    for (Class<? extends IQueryHandler> handler : queryHandlers) {
      ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];
      Class<? extends IQuery> queryClass =
          (Class<? extends IQuery>) paramType.getActualTypeArguments()[0];
      handlers.put(queryClass, handler);
    }

    return handlers;
  }
}
