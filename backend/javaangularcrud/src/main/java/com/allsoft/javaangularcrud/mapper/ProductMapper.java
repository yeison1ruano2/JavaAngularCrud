package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.dto.ProductImageDto;
import com.allsoft.javaangularcrud.entity.Product;
import com.allsoft.javaangularcrud.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductMapper {
  private final ProductImageMapper productImageMapper;
  private final ProductRepository productRepository;

  public ProductMapper(ProductImageMapper productImageMapper, ProductRepository productRepository) {
    this.productImageMapper = productImageMapper;
    this.productRepository = productRepository;
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

  public ProductDto entityToDtoUpdateProduct(Product product){
    return new ProductDto(
            product.getNombre(),
            product.getDescripcion(),
            product.getPrecio(),
            product.getStock(),
            product.getCategoria(),
            product.getMarca(),
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

  public Product entityUpdate(Product productDb, ProductDto productDto) {
    productDb.setNombre(productDto.getNombre());
    productDb.setDescripcion(productDto.getDescripcion());
    productDb.setPrecio(productDto.getPrecio());
    productDb.setStock(productDto.getStock());
    productDb.setCategoria(productDto.getCategoria());
    productDb.setMarca(productDto.getMarca());
    return productDb;
  }

  public ProductDto onlyEntityToDto(Long  productId) {
    Optional<Product> optionalProduct = productRepository.findByNombreAndStatusTrue(productId.toString());
    Product product = optionalProduct.get();
    return new ProductDto(
            product.getNombre(),
            product.getDescripcion(),
            product.getCategoria(),
            product.getMarca(),
            product.getStatus()
    );
  }
}
