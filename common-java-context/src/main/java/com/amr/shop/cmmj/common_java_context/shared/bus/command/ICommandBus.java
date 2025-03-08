package com.amr.shop.cmmj.common_java_context.shared.bus.command;

public interface ICommandBus {
  void dispatch(ICommand command) throws CommandHandlerExecutionException;
}
