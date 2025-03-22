package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.entity.ProductImage;
import com.allsoft.javaangularcrud.repository.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
  private final ProductImageRepository productImageRepository;

  public ProductImageService(ProductImageRepository productImageRepository) {
    this.productImageRepository = productImageRepository;
  }

  public List<ProductImage> findByProductId(Long id) {
    return this.productImageRepository.findByProductIdAndStatusTrue(id);
  }
}
