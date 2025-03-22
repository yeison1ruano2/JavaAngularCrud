package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.dto.ProductImageDto;
import com.allsoft.javaangularcrud.entity.Product;
import com.allsoft.javaangularcrud.entity.ProductImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMapper {
  private final ProductImageMapper productImageMapper;

  public ProductMapper(ProductImageMapper productImageMapper) {
    this.productImageMapper = productImageMapper;
  }

  public Product dtoToEntity(ProductDto productDto){
    return new Product(
            productDto.getNombre(),
            productDto.getDescripcion(),
            productDto.getPrecio(),
            productDto.getStock(),
            productDto.getCategoria(),
            productDto.getMarca()
    );
  }

  public ProductDto entityToDto(Product product,List<ProductImageDto> listProductImageDto){
    return new ProductDto(
            product.getNombre(),
            product.getDescripcion(),
            product.getPrecio(),
            product.getStock(),
            product.getCategoria(),
            product.getMarca(),
            listProductImageDto,
            product.getStatus()
    );
  }


  public List<ProductDto> entityListToDtoList(List<Product> products){
    return products.stream()
            .map(product -> {
              List<ProductImageDto> listProductImageDto =  this.productImageMapper.entityListToDtoList(product.getImagenes());
              return entityToDto(product,listProductImageDto);
            })
            .toList();
  }

  public Product entityUpdate(Product productDb, ProductDto productDto,List<ProductImage> listProductImage) {
    productDb.setNombre(productDto.getNombre());
    productDb.setDescripcion(productDto.getDescripcion());
    productDb.setPrecio(productDto.getPrecio());
    productDb.setStock(productDto.getStock());
    productDb.setCategoria(productDto.getCategoria());
    productDb.setMarca(productDto.getMarca());
    productDb.setImagenes(listProductImage);
    return productDb;
  }
}
