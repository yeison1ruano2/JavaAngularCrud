package com.allsoft.javaangularcrud.dto;

import com.allsoft.javaangularcrud.entity.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UserDto{
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String nombre;

  @NotEmpty(message = "El apellido no puede estar vacío")
  private String apellido;

  @NotEmpty(message = "El correo no puede estar vacío")
  @Email(message = "Debe proporcionar un correo válido")
  private String email;

  private String direccion;

  @NotEmpty(message = "El numero de celular no puede estar vacio")
  private String numCell;

  @NotEmpty(message = "El nombre de usuario no puede estar vacio")
  private String username;

  @NotEmpty(message = "La contraseña no puede estar vacio")
  @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
  private String password;

  private String rePassword;

  private Set<RoleName> roles;

  public UserDto(String apellido, String direccion, String email, String nombre,
                 String numCell, String password, String rePassword, Set<RoleName> roles,
                 String username) {
    this.apellido = apellido;
    this.direccion = direccion;
    this.email = email;
    this.nombre = nombre;
    this.numCell = numCell;
    this.password = password;
    this.rePassword = rePassword;
    this.roles = roles;
    this.username = username;
  }

  public UserDto(String apellido, String direccion, String email, String nombre,
                 String numCell,String username) {
    this.apellido = apellido;
    this.direccion = direccion;
    this.email = email;
    this.nombre = nombre;
    this.numCell = numCell;
    this.username = username;
  }

  public UserDto() {
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNumCell() {
    return numCell;
  }

  public void setNumCell(String numCell) {
    this.numCell = numCell;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRePassword() {
    return rePassword;
  }

  public void setRePassword(String rePassword) {
    this.rePassword = rePassword;
  }

  public Set<RoleName> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleName> roles) {
    this.roles = roles;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
