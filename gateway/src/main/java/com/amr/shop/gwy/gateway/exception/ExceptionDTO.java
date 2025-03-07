package com.amr.shop.gwy.gateway.exception;

import java.util.List;

public class ExceptionDTO {
  private final int code;
  private final String businessCode;
  private final List<String> messages;

  public ExceptionDTO(int code, String businessCode, List<String> messages) {
    this.code = code;
    this.businessCode = businessCode;
    this.messages = messages;
  }

  public int getCode() {
    return code;
  }

  public String getBusinessCode() {
    return businessCode;
  }

  public List<String> getMessages() {
    return messages;
  }
}
