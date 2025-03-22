package com.allsoft.javaangularcrud.controller;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.dto.ProductImageDto;
import com.allsoft.javaangularcrud.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

  @PutMapping(consumes="multipart/form-data",  value="/{id}")
  public ResponseEntity<Map<String,String>> updateProduct(@PathVariable Long id,
                                                          @RequestParam("product") String productDto,
                                                          @RequestPart("images") List<MultipartFile> images)
  {
    return this.productService.updateProduct(id,productDto,images);
  }

  @GetMapping
  public ResponseEntity<List<ProductDto>> findAllProduct(){
    return this.productService.findAllProduct();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> findById(@PathVariable Long id){
    return this.productService.findById(id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String,String>> deleteProduct(@PathVariable Long id){
    return this.productService.deleteProduct(id);
  }

  @PostMapping("/search")
  public ResponseEntity<List<ProductDto>> findByName(@RequestBody ProductDto productDto){
    return this.productService.findByFilters(productDto);
  }

  @GetMapping("/{productId}/images")
  public ResponseEntity<List<ProductImageDto>> getProductImages(@PathVariable Long productId) {
    return this.productService.getProductImages(productId);
  }
}
