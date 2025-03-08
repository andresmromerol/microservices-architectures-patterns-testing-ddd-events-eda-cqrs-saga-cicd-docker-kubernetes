package com.amr.shop.usr.user_context.user.application.create_user.customer;

import com.amr.shop.cmmj.common_java_context.shared.bus.command.ICommand;
import java.util.UUID;

public record UserCreateCustomerCmd(
    UUID id, String name, String email, String phone, String address) implements ICommand {}
