package com.allsoft.javaangularcrud.dto;

public class ProductImageDto{
  private String imageUrl;
  private String message;

  public ProductImageDto(String imageUrl,String message) {
    this.imageUrl = imageUrl;
    this.message = message;
  }

  public ProductImageDto() {
  }

  public ProductImageDto(String message){
    this.message = message;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
