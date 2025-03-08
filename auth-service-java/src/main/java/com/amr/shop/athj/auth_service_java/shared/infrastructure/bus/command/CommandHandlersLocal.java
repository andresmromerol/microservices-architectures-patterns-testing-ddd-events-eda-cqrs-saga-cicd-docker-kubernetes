package com.amr.shop.athj.auth_service_java.shared.infrastructure.bus.command;

import com.amr.shop.cmmj.common_java_context.services.auth.CQBusEnum;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.CommandNotRegisteredException;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommand;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public final class CommandHandlersLocal {

  HashMap<Class<? extends ICommand>, Class<? extends ICommandHandler>> indexedCommandHandlers;

  public CommandHandlersLocal() {
    String path = CQBusEnum.AUTH_SERVICE_JAVA.getValue();
    Reflections reflections = new Reflections(path);
    Set<Class<? extends ICommandHandler>> classes =
        reflections.getSubTypesOf(ICommandHandler.class);
    indexedCommandHandlers = formatHandlers(classes);
  }

  public Class<? extends ICommandHandler> search(Class<? extends ICommand> commandClass)
      throws CommandNotRegisteredException {
    Class<? extends ICommandHandler> commandHandlerClass = indexedCommandHandlers.get(commandClass);
    if (null == commandHandlerClass) {
      throw new CommandNotRegisteredException(commandClass);
    }
    return commandHandlerClass;
  }

  private HashMap<Class<? extends ICommand>, Class<? extends ICommandHandler>> formatHandlers(
      Set<Class<? extends ICommandHandler>> commandHandlers) {
    HashMap<Class<? extends ICommand>, Class<? extends ICommandHandler>> handlers = new HashMap<>();
    for (Class<? extends ICommandHandler> handler : commandHandlers) {
      ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];
      Class<? extends ICommand> commandClass =
          (Class<? extends ICommand>) paramType.getActualTypeArguments()[0];
      handlers.put(commandClass, handler);
    }

    return handlers;
  }
}
