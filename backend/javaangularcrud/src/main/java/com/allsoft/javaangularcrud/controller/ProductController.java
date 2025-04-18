package com.allsoft.javaangularcrud.controller;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.dto.ProductImageDto;
import com.allsoft.javaangularcrud.services.ProductService;
import com.allsoft.javaangularcrud.services.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductServiceImpl productService) {
    this.productService = productService;
  }

  @PostMapping(consumes="multipart/form-data",value="/save")
  public ResponseEntity<ProductDto> saveProduct(
          @RequestParam("product") String productDto,
          @RequestPart("images") List<MultipartFile> images){
      return productService.saveProduct(productDto,images);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto)
  {
    return productService.updateProduct(id,productDto);
  }

  @PutMapping(consumes="multipart/form-data",value="/updateImage/{productId}")
  public ResponseEntity<List<ProductImageDto>> updateImages(
          @PathVariable Long productId,
          @RequestPart("images") List<MultipartFile> images)
  {
    return productService.updateImages(images,productId);
  }

  @GetMapping
  public ResponseEntity<List<ProductDto>> findAllProduct(){return productService.findAllProduct();}

  @DeleteMapping()
  @PreAuthorize("hasAnyRole('VENDEDOR', 'ADMIN')")
  public ResponseEntity<Map<String,String>> deleteProduct(@RequestBody ProductDto productDto){
    return productService.deleteProduct(productDto);
  }

  @PostMapping("/search")
  public ResponseEntity<List<ProductDto>> findByName(@RequestBody ProductDto productDto){
    return productService.findByFilters(productDto);
  }

  @GetMapping("/{productId}/images")
  public ResponseEntity<List<ProductImageDto>> getProductImages(@PathVariable Long productId) {
    return productService.getProductImages(productId);
  }
}
