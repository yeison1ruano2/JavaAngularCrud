package com.allsoft.javaangularcrud.dto;

import java.util.List;

public class OrderDto {
  private Long id;
  private List<OrderItemDto> items;
  private Double totalAmount;
  private String status;
  private String username;
  private String message;

  public OrderDto(Long id, List<OrderItemDto> items, String status, Double totalAmount, String username, String message) {
    this.id = id;
    this.items = items;
    this.status = status;
    this.totalAmount = totalAmount;
    this.username = username;
    this.message= message;
  }

  public OrderDto() {
  }

  public OrderDto(String message) {
    this.message = message;
  }

  public List<OrderItemDto> getItems() {
    return items;
  }

  public void setItems(List<OrderItemDto> items) {
    this.items = items;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
