package com.amr.shop.athj.auth_service_java.shared.infrastructure.bus.command;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.CommandHandlerExecutionException;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommand;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandBus;
import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommandHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public final class InMemoryCommandBus implements ICommandBus {

  private final CommandHandlersLocal information;

  private final ApplicationContext context;

  public InMemoryCommandBus(CommandHandlersLocal information, ApplicationContext context) {

    this.information = information;
    this.context = context;
  }

  @Override
  public void dispatch(ICommand command) throws CommandHandlerExecutionException {
    try {
      Class<? extends ICommandHandler> commandHandlerClass = information.search(command.getClass());
      ICommandHandler handler = context.getBean(commandHandlerClass);
      handler.handle(command);
    } catch (Throwable error) {
      throw new CommandHandlerExecutionException(error);
    }
  }
}
