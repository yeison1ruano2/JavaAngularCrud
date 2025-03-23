package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.entity.ProductImage;

import java.util.List;

public interface ProductImageService {
  List<ProductImage> findByProductIdAndStatusTrue(Long productId);
  void save(ProductImage productImage);
}
