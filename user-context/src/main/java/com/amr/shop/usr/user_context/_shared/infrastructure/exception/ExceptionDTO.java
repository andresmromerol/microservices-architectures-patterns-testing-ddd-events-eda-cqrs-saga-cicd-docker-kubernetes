package com.amr.shop.usr.user_context._shared.infrastructure.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExceptionDTO {
  private final int code;
  private final String businessCode;
  private final List<String> messages;
}
