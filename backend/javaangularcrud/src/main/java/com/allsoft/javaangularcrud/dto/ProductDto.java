package com.allsoft.javaangularcrud.dto;

import java.util.List;

public class ProductDto{
  private String nombre;
  private String descripcion;
  private Double precio;
  private Integer stock;
  private String categoria;
  private String marca;
  private List<ProductImageDto> imagenes;
  private Boolean status;

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

  public ProductDto() {
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
