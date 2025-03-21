package com.allsoft.javaangularcrud.dto;

import com.allsoft.javaangularcrud.entity.ProductImage;

import java.util.List;

public record ProductDto (
        String nombre,
        String descripcion,
        Double precio,
        Integer stock,
        String categoria,
        String marca,
        List<ProductImage> imagenes,
        Boolean status)
{
}
