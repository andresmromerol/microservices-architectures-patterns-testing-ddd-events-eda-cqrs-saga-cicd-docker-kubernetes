package com.amr.shop.cmmj.common_java_context.services.auth.event;

import com.amr.shop.cmmj.common_java_context.shared.event.DomainEvent;

public class AdministratorRegisteredEvent extends DomainEvent {

  private String name;
  private String email;
  private String createdByAdminId;
  private String phone;

  public AdministratorRegisteredEvent() {}

  public AdministratorRegisteredEvent(
      String aggregateId, String name, String email, String createdByAdminId, String phone) {
    super(aggregateId);
    this.name = name;
    this.email = email;
    this.createdByAdminId = createdByAdminId;
    this.phone = phone;
  }

  public AdministratorRegisteredEvent(
      String aggregateId,
      String eventId,
      String occurredOn,
      String name,
      String email,
      String createdByAdminId,
      String phone) {
    super(aggregateId, eventId, occurredOn);
    this.name = name;
    this.email = email;
    this.createdByAdminId = createdByAdminId;
    this.phone = phone;
  }

  public AdministratorRegisteredEvent(
      String name, String email, String createdByAdminId, String phone) {
    this.name = name;
    this.email = email;
    this.createdByAdminId = createdByAdminId;
    this.phone = phone;
  }

  @Override
  public String eventName() {
    return "amr.shop.event.v1.auth.administrator.registered";
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

  public String getCreatedByAdminId() {
    return createdByAdminId;
  }

  public void setCreatedByAdminId(String createdByAdminId) {
    this.createdByAdminId = createdByAdminId;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
