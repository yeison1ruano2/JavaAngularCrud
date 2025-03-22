package com.allsoft.javaangularcrud.dto;

public class ProductImageDto{
  private String imageUrl;

  public ProductImageDto(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
