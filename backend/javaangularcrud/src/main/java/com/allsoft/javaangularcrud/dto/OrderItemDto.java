package com.allsoft.javaangularcrud.dto;

public class OrderItemDto {
 private ProductDto productDto;
 private Integer quantity;
 private Double price;

  public OrderItemDto(Double price, ProductDto productDto, Integer quantity) {
    this.price = price;
    this.productDto = productDto;
    this.quantity = quantity;
  }

  public OrderItemDto() {
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
