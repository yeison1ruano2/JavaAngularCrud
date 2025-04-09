package com.allsoft.javaangularcrud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class ProductImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String imageUrl;

  //@ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  @JsonBackReference
  private Long productId;

  public ProductImage() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Long getProduct() {
    return productId;
  }

  public void setProduct(Long product) {
    this.productId = product;
  }

  public ProductImage(String imageUrl, Long productId) {
    this.imageUrl = imageUrl;
    this.productId = productId;
  }
}
