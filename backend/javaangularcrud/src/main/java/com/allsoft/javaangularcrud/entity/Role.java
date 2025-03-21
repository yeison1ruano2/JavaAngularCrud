package com.allsoft.javaangularcrud.entity;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(EnumType.STRING)
  @Column(unique=true, nullable = false)
  private RoleName name;

  public Role() {}

  public Role(RoleName name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public RoleName getName() {
    return name;
  }

  public void setName(RoleName name) {
    this.name = name;
  }
}

