package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
  @Query("SELECT pi FROM ProductImage pi WHERE pi.product.id = :productId AND pi.product.status = true")
  List<ProductImage> findByProductIdAndStatusTrue(@Param("productId")Long productId);

}
