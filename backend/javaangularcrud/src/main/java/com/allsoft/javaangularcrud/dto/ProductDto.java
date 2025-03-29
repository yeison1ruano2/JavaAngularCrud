package com.allsoft.javaangularcrud.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ProductDto{
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String nombre;

  @NotEmpty(message = "La descripcion no puede estar vacío")
  private String descripcion;

  @NotEmpty(message = "El precio no puede estar vacío")
  private Double precio;

  @NotEmpty(message = "El stock no puede estar vacío")
  private Integer stock;

  @NotEmpty(message = "La categoria no puede estar vacío")
  private String categoria;

  @NotEmpty(message = "La marca no puede estar vacío")
  private String marca;

  private List<ProductImageDto> imagenes;
  private Boolean status;
  private String message;

  public ProductDto(String nombre, String descripcion, Double precio, Integer stock, String categoria, String marca, List<ProductImageDto> imagenes, Boolean status) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.stock = stock;
    this.categoria = categoria;
    this.marca = marca;
    this.imagenes = imagenes;
    this.status = status;
  }
  public ProductDto(String nombre, String descripcion, Double precio, Integer stock, String categoria, String marca,Boolean status) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.stock = stock;
    this.categoria = categoria;
    this.marca = marca;
    this.status = status;
  }

  public ProductDto(String nombre, String descripcion,  String categoria, String marca,Boolean status) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.marca = marca;
    this.status = status;
  }


  public ProductDto() {
  }
  public ProductDto(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Double getPrecio() {
    return precio;
  }

  public void setPrecio(Double precio) {
    this.precio = precio;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public List<ProductImageDto> getImagenes() {
    return imagenes;
  }

  public void setImagenes(List<ProductImageDto> imagenes) {
    this.imagenes = imagenes;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
}
