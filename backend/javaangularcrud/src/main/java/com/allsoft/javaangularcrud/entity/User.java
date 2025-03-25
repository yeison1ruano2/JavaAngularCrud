package com.allsoft.javaangularcrud.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

@Entity
@Table(name="users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String nombre;

  @Column(nullable = false)
  @NotEmpty(message = "El apellido no puede estar vacío")
  private String apellido;

  @Column(nullable = false)
  @NotEmpty(message = "El correo no puede estar vacío")
  @Email(message = "Debe proporcionar un correo válido")
  private String email;

  @Column(nullable = true)
  private String direccion;

  @Column(nullable = false, unique = true)
  private String numCell;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles;

  @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
  private Cart cart;

  public User() {}

  public User(String nombre, String apellido, String email, String direccion,
              String numCell, String username, String password, Set<Role> roles,
              Cart cart) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.direccion = direccion;
    this.numCell = numCell;
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.cart = cart;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getNumCell() {
    return numCell;
  }

  public void setNumCell(String numCell) {
    this.numCell = numCell;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
  }
}
