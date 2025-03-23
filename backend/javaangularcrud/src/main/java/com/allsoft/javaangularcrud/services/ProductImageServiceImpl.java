package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.entity.ProductImage;
import com.allsoft.javaangularcrud.repository.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
  private final ProductImageRepository productImageRepository;

  public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
    this.productImageRepository = productImageRepository;
  }

  @Override
  public List<ProductImage> findByProductIdAndStatusTrue(Long productId) {
    return this.productImageRepository.findByProductIdAndStatusTrue(productId);
  }

  @Override
  public void save(ProductImage productImage) {
    this.productImageRepository.save(productImage);
  }
}
