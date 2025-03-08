package com.amr.shop.cmmj.common_java_context.shared.bus.command;

public interface ICommandHandler<T extends ICommand> {
  void handle(T command);
}
