package com.allsoft.javaangularcrud.dto;

public class CartItemDto {
  private ProductDto productDto;
  private int cantidad;

  public CartItemDto(ProductDto productDto, int cantidad) {
    this.cantidad = cantidad;
    this.productDto = productDto;
  }

  public CartItemDto() {
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public ProductDto getProductDto() {
    return productDto;
  }

  public void setProductDto(ProductDto productDto) {
    this.productDto = productDto;
  }
}
