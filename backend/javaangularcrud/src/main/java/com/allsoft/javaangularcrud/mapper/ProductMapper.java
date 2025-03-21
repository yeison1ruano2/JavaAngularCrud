package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMapper {

  public Product dtoToEntity(ProductDto productDto){
    return new Product(
            productDto.nombre(),
            productDto.descripcion(),
            productDto.precio(),
            productDto.stock(),
            productDto.categoria(),
            productDto.marca(),
            productDto.imagenes()
    );
  }

  public ProductDto entityToDto(Product product){
    return new ProductDto(
            product.getNombre(),
            product.getDescripcion(),
            product.getPrecio(),
            product.getStock(),
            product.getCategoria(),
            product.getMarca(),
            product.getImagenes(),
            product.getStatus()
    );
  }


  public List<ProductDto> entityListToDtoList(List<Product> products){
    return products.stream()
            .map(this::entityToDto)
            .toList();
  }

  public Product entityUpdate(Product productDb, ProductDto productDto) {
    productDb.setNombre(productDto.nombre());
    productDb.setDescripcion(productDto.descripcion());
    productDb.setPrecio(productDto.precio());
    productDb.setStock(productDto.stock());
    productDb.setCategoria(productDto.categoria());
    productDb.setMarca(productDto.marca());
    productDb.setImagenes(productDto.imagenes());
    return productDb;
  }
}
