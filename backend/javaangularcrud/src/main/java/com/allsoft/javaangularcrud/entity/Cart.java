package com.allsoft.javaangularcrud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="carts")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name="user_id",nullable = false)
  @JsonBackReference
  private User user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,orphanRemoval = true)
  private List<CartItem> items;

  public Cart(Long id, List<CartItem> items, User user) {
    this.id = id;
    this.items = items;
    this.user = user;
  }

  public Cart() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<CartItem> getItems() {
    return items;
  }

  public void setItems(List<CartItem> items) {
    this.items = items;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
