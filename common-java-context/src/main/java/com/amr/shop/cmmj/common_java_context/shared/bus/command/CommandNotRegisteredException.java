package com.amr.shop.cmmj.common_java_context.shared.bus.command;

import static com.amr.shop.cmmj.common_java_context.shared.enums.ApplicationValidationEnum.COMMAND_EXCEPTION;

public class CommandNotRegisteredException extends Exception {

    public CommandNotRegisteredException(Class<? extends ICommand> command) {

        super(String.format(COMMAND_EXCEPTION.getMessage(), command.toString()));
    }
}
