package com.allsoft.javaangularcrud.entity;

import jakarta.persistence.*;

@Entity
@Table(name="cart_items")
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;

  //@ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Long productId;

  @Column(nullable = false)
  private int cantidad;

  public CartItem(Long id, Cart cart, Long productId, int cantidad) {
    this.id = id;
    this.cart = cart;
    this.productId = productId;
    this.cantidad = cantidad;
  }

  public CartItem() {
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }
}
