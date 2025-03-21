package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
  List<ProductImage> findByProductId(Long productId);

}
