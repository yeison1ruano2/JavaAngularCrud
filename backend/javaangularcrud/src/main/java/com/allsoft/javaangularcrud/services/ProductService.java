package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.dto.ProductImageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProductService {
  ResponseEntity<Map<String,String>> saveProduct(String productDto, List<MultipartFile> images);
  ResponseEntity<Map<String,String>> updateProduct(Long id, ProductDto productDto);
  ResponseEntity<Map<String,String>> updateImages(List<MultipartFile> images,Long productId);
  ResponseEntity<List<ProductDto>> findAllProduct();
  ResponseEntity<ProductDto> findById(Long id);
  ResponseEntity<Map<String,String>> deleteProduct(Long id);
  ResponseEntity<List<ProductDto>> findByFilters(ProductDto productDto);
  ResponseEntity<List<ProductImageDto>> getProductImages(Long productId);
}
