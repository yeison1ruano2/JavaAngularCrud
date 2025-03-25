package com.allsoft.javaangularcrud.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="carts")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name="user_id",nullable = false)
  private User user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,orphanRemoval = true)
  private Set<CartItem> items;

  public Cart(Long id, Set<CartItem> items, User user) {
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

  public Set<CartItem> getItems() {
    return items;
  }

  public void setItems(Set<CartItem> items) {
    this.items = items;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
