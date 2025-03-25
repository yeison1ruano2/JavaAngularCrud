package com.allsoft.javaangularcrud.dto;

import java.util.Set;

public class CartDto {

  private UserDto user;
  private Set<CartItemDto> items;

  public CartDto(UserDto user, Set<CartItemDto> items) {
    this.items = items;
    this.user = user;
  }

  public CartDto() {
  }

  public Set<CartItemDto> getItems() {
    return items;
  }

  public void setItems(Set<CartItemDto> items) {
    this.items = items;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }
}
