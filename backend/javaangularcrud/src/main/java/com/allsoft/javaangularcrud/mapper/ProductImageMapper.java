package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.ProductImageDto;
import com.allsoft.javaangularcrud.entity.ProductImage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageMapper {
  public List<ProductImageDto> entityListToDtoList(List<ProductImage> productImages) {
    return productImages.stream()
            .map(this::entityToDto)
            .toList();
  }

  public ProductImageDto entityToDto(ProductImage productImage) {
    return new ProductImageDto(
            productImage.getImageUrl()
    );
  }
}
