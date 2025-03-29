package com.allsoft.javaangularcrud.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "productos")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  @NotEmpty(message = "El nombre no puede estar vacío")
  private String nombre;
  @Column(nullable = false, columnDefinition = "TEXT")
  @NotEmpty(message = "La descripcion no puede estar vacío")
  private String descripcion;
  @Column(nullable = false)
  @NotEmpty(message = "El precio no puede estar vacío")
  private Double precio;
  @Column(nullable = false)
  @NotEmpty(message = "El stock no puede estar vacío")
  private Integer stock;
  @Column(nullable = false, length = 50)
  @NotEmpty(message = "La categoria no puede estar vacío")
  private String categoria;
  @Column(length = 50)
  @NotEmpty(message = "La marca no puede estar vacío")
  private String marca;
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<ProductImage> imagenes;
  private Boolean status=true;

  public Product(String nombre, String descripcion, Double precio, Integer stock, String categoria, String marca,List<ProductImage> imagenes) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.stock = stock;
    this.categoria = categoria;
    this.marca = marca;
    this.imagenes = imagenes;
  }

  public Product(String nombre, String descripcion, Double precio, Integer stock, String categoria, String marca) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.stock = stock;
    this.categoria = categoria;
    this.marca = marca;
  }

  public Product() {
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Double getPrecio() {
    return precio;
  }

  public void setPrecio(Double precio) {
    this.precio = precio;
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

  public List<ProductImage> getImagenes() {
    return imagenes;
  }

  public void setImagenes(List<ProductImage> imagenes) {
    this.imagenes = imagenes;
  }
}
