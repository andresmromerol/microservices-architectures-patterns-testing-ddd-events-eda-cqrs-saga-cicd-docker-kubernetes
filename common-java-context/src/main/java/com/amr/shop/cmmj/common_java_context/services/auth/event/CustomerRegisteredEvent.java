package com.amr.shop.cmmj.common_java_context.services.auth.event;

import com.amr.shop.cmmj.common_java_context.shared.event.DomainEvent;

public class CustomerRegisteredEvent extends DomainEvent {

  private String name;
  private String email;
  private String address;
  private String phone;

  public CustomerRegisteredEvent() {}

  public CustomerRegisteredEvent(
      String aggregateId, String name, String email, String address, String phone) {
    super(aggregateId);
    this.name = name;
    this.email = email;
    this.address = address;
    this.phone = phone;
  }

  public CustomerRegisteredEvent(
      String aggregateId,
      String eventId,
      String occurredOn,
      String name,
      String email,
      String address,
      String phone) {
    super(aggregateId, eventId, occurredOn);
    this.name = name;
    this.email = email;
    this.address = address;
    this.phone = phone;
  }

  public CustomerRegisteredEvent(String name, String email, String address, String phone) {
    this.name = name;
    this.email = email;
    this.address = address;
    this.phone = phone;
  }

  @Override
  public String eventName() {
    return "amr.shop.event.v1.auth.customer.registered";
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
