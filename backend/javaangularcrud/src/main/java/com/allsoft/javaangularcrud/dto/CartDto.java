package com.allsoft.javaangularcrud.dto;

import java.util.List;

public class CartDto {

  private UserDto user;
  private List<CartItemDto> items;
  private String message;

  public CartDto(UserDto user, List<CartItemDto> items) {
    this.items = items;
    this.user = user;
  }

  public CartDto() {
  }

  public CartDto(String message) {
    this.message = message;
  }

  public List<CartItemDto> getItems() {
    return items;
  }

  public void setItems(List<CartItemDto> items) {
    this.items = items;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
