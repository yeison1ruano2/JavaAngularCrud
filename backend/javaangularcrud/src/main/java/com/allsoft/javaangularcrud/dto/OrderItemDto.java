package com.allsoft.javaangularcrud.dto;

public class OrderItemDto {
 private OrderDto orderDto;
 private ProductDto productDto;
 private Integer quantity;
 private Double price;

  public OrderItemDto(OrderDto orderDto, Double price, ProductDto productDto, Integer quantity) {
    this.orderDto = orderDto;
    this.price = price;
    this.productDto = productDto;
    this.quantity = quantity;
  }

  public OrderItemDto() {
  }

  public OrderDto getOrderDto() {
    return orderDto;
  }

  public void setOrderDto(OrderDto orderDto) {
    this.orderDto = orderDto;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public ProductDto getProductDto() {
    return productDto;
  }

  public void setProductDto(ProductDto productDto) {
    this.productDto = productDto;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
