package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.dto.ProductImageDto;
import com.allsoft.javaangularcrud.entity.Product;
import com.allsoft.javaangularcrud.entity.ProductImage;
import com.allsoft.javaangularcrud.mapper.ProductImageMapper;
import com.allsoft.javaangularcrud.mapper.ProductMapper;
import com.allsoft.javaangularcrud.repository.ProductImageRepository;
import com.allsoft.javaangularcrud.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService {
  private static final String ERROR = "error";
  private static final String MESSAGE = "message";
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final ProductImageRepository productImageRepository;
  private final CloudinaryService cloudinaryService;
  private final ObjectMapper objectMapper;
  private final ProductImageMapper productImageMapper;
  @Autowired
  public ProductService(ProductRepository productRepository, ProductMapper productMapper,  ProductImageRepository productImageRepository, CloudinaryService cloudinaryService, ObjectMapper objectMapper, ProductImageMapper productImageMapper) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
    this.productImageRepository = productImageRepository;
    this.cloudinaryService = cloudinaryService;
    this.objectMapper = objectMapper;
    this.productImageMapper = productImageMapper;
  }

  public ResponseEntity<Map<String,String>> saveProduct(String productDto, List<MultipartFile> images) {
    try {
      ProductDto productDtoConvert = this.objectMapper.readValue(productDto,ProductDto.class);
      Product product = this.productMapper.dtoToEntity(productDtoConvert);
      this.productRepository.save(product);

      List<ProductImage> productImages = this.saveImageProduct(images,product);
      product.setImagenes(productImages);

      Map<String, String> response = new HashMap<>();
      response.put(MESSAGE, "Producto creado con éxito");
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }catch (JsonProcessingException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(ERROR, "Error al procesar JSON: " + e.getMessage()));
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(ERROR, "Error al subir imágenes: " + e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(ERROR, "Error inesperado: " + e.getMessage()));
    }
  }

  public ResponseEntity<Map<String,String>> updateProduct(Long id,String productDto,List<MultipartFile> images) {
    Map<String, String> response = new HashMap<>();
    try {
      Optional<Product> product=productRepository.findById(id);
      if(product.isPresent()){
        ProductDto productDtoConvert = this.objectMapper.readValue(productDto,ProductDto.class);
        Product productDb = product.get();
        List<ProductImage> listProductImageUpdated=  this.updateProductImage(images,productDb);
        Product updateProduct = this.productMapper.entityUpdate(productDb,productDtoConvert,listProductImageUpdated);
        productRepository.save(updateProduct);
        response.put(MESSAGE,"Producto actualizado con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(response);
      }else{
        response.put(MESSAGE,"Producto no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }
    }catch(Exception e){
      response.put(ERROR,"Error al actualizar el producto: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  private List<ProductImage> updateProductImage(List<MultipartFile> images, Product productDb) throws IOException{
    List<ProductImage>productImages = new ArrayList<>();
    for(MultipartFile image : images){
      ProductImage productImage = new ProductImage();
      productImage.setProduct(productDb);
      this.productImageRepository.save(productImage);
      String productName = productDb.getNombre().replace(" ","_");
      String imageName = productName + "_" + productImage.getId();
      String imageUrl = this.cloudinaryService.updateFile(image,imageName);
      productImage.setImageUrl(imageUrl);
      this.productImageRepository.save(productImage);
      productImages.add(productImage);
    }
    return productImages;
  }

  public List<ProductImage> saveImageProduct(List<MultipartFile> images,Product product) throws IOException {
    List<ProductImage> productImages = new ArrayList<>();
    for (MultipartFile image : images) {
      ProductImage productImage = new ProductImage();
      productImage.setProduct(product);
      this.productImageRepository.save(productImage);
      String productName = product.getNombre().replace(" ","_");
      String imageName = productName + "_" + productImage.getId();
      String imageUrl = this.cloudinaryService.uploadFile(image, imageName);
      productImage.setImageUrl(imageUrl);
      this.productImageRepository.save(productImage);
      productImages.add(productImage);
    }
    return productImages;
  }

  public ResponseEntity<List<ProductDto>> findAllProduct() {
    try {
      List<Product> listProduct = this.productRepository.findByStatusTrue();
      if(!listProduct.isEmpty()){
        List<ProductDto> listProductDto = this.productMapper.entityListToDtoList(listProduct);
        return ResponseEntity.status(HttpStatus.OK).body(listProductDto);
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
      }
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(List.of());
    }
  }

  public ResponseEntity<ProductDto> findById(Long id) {
    ProductDto productDto = new ProductDto();
    try {
      Optional<Product> optionalProduct =this.productRepository.findByIdAndStatusTrue(id);
      if(optionalProduct.isPresent()){
        Product productDb = optionalProduct.get();
        List<ProductImage> listProductImage = productDb.getImagenes();
        List<ProductImageDto> listProductImageDto = this.productImageMapper.entityListToDtoList(listProductImage);
        productDto = this.productMapper.entityToDto(productDb,listProductImageDto);
        return ResponseEntity.status(HttpStatus.OK).body(productDto);
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productDto);
      }
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  public ResponseEntity<Map<String,String>> deleteProduct(Long id) {
    Map<String, String> response = new HashMap<>();
    try {
      Optional<Product> product = productRepository.findById(id);
      if(product.isPresent()){
        Product productDb = product.get();
        productDb.setStatus(false);
        productRepository.save(productDb);
        response.put(MESSAGE,"Producto eliminado con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(response);
      }else{
        response.put(MESSAGE,"Producto no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }
    }catch(Exception e){
      response.put(ERROR,"Error al eliminar el producto: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  public ResponseEntity<List<ProductDto>> findByFilters(ProductDto productDto) {
    try {
      List<Product> product = this.productRepository.findByTerm(productDto.getNombre(),productDto.getMarca(),productDto.getCategoria());
      if(!product.isEmpty()){
        List<ProductDto> productDtoBackend = this.productMapper.entityListToDtoList(product);
        return ResponseEntity.status(HttpStatus.OK).body(productDtoBackend);
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
      }
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
    }
  }

  public ResponseEntity<List<ProductImageDto>> getProductImages(Long productId) {
    try {
      List<ProductImage> productImages = this.productImageRepository.findByProductIdAndStatusTrue(productId);
      if(!productImages.isEmpty()){
        List<ProductImageDto> productImagesDto = this.productImageMapper.entityListToDtoList(productImages);
        return ResponseEntity.status(HttpStatus.OK).body(productImagesDto);
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
      }
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
    }
  }
}
