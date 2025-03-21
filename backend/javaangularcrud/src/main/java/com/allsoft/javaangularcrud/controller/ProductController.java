package com.allsoft.javaangularcrud.controller;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.entity.ProductImage;
import com.allsoft.javaangularcrud.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping(consumes="multipart/form-data",value="/save")
  public ResponseEntity<Map<String,String>> saveProduct(
          @RequestParam("product") String productDto,
          @RequestPart("images") List<MultipartFile> images){
      return this.productService.saveProduct(productDto,images);
  }

  @GetMapping
  public ResponseEntity<List<ProductDto>> findAllProduct(){
    return ResponseEntity.ok(this.productService.findAllProduct());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> findById(@PathVariable Long id){
    return ResponseEntity.ok(this.productService.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String,String>> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
    Map<String, String> response = new HashMap<>();
    response.put("message", this.productService.updateProduct(id,productDto));
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String,String>> deleteProduct(@PathVariable Long id){
    Map<String,String> response = new HashMap<>();
    response.put("message",this.productService.deleteProduct(id));
    return ResponseEntity.ok(response);
  }

  @PostMapping("/search")
  public ResponseEntity<List<ProductDto>> findByName(@RequestBody ProductDto productDto){
    return ResponseEntity.ok(this.productService.findByFilters(productDto));
  }

  @GetMapping("/{productId}/images")
  public ResponseEntity<List<ProductImage>> getProductImages(@PathVariable Long productId) {
   return ResponseEntity.ok(this.productService.getProductImages(productId));
  }
}
