package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.dto.ProductImageDto;
import com.allsoft.javaangularcrud.entity.Product;
import com.allsoft.javaangularcrud.entity.ProductImage;
import com.allsoft.javaangularcrud.mapper.ProductImageMapper;
import com.allsoft.javaangularcrud.mapper.ProductMapper;
import com.allsoft.javaangularcrud.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService{
  private static final String ERROR = "error";
  private static final String MESSAGE = "message";
  private static final String ERROR_INESPERADO = "Error inesperado: ";
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final ProductImageService productImageService;
  private final CloudinaryService cloudinaryService;
  private final ObjectMapper objectMapper;
  private final ProductImageMapper productImageMapper;
  @Autowired
  public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductImageService productImageService, CloudinaryService cloudinaryService, ObjectMapper objectMapper, ProductImageMapper productImageMapper) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
    this.productImageService = productImageService;
    this.cloudinaryService = cloudinaryService;
    this.objectMapper = objectMapper;
    this.productImageMapper = productImageMapper;
  }

  public ResponseEntity<ProductDto> saveProduct(String productDto, List<MultipartFile> images) {
    try {
      ProductDto productDtoConvert = objectMapper.readValue(productDto,ProductDto.class);
      Product product = productMapper.dtoToEntity(productDtoConvert);
      this.productRepository.save(product);
      List<ProductImage> productImages = saveImageProduct(images,product);
      product.setImagenes(productImages);
      List<ProductImageDto> listProductImagesDto = productImageMapper.entityListToDtoList(productImages);
      ProductDto productDtoSend= productMapper.entityToDto(product,listProductImagesDto);
      return ResponseEntity.status(HttpStatus.OK).body(productDtoSend);
    }catch (JsonProcessingException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductDto("Error al procesar JSON: " + e.getMessage()));
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductDto("Error al subir imágenes: " + e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductDto(ERROR_INESPERADO + e.getMessage()));
    }
  }

  public ResponseEntity<ProductDto> updateProduct(Long id,ProductDto productDto) {
    try {
      Optional<Product> product = productRepository.findById(id);
      if(product.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductDto("Producto no encontrado"));
      }
      Product productDb = product.get();
      Product updateProduct = productMapper.entityUpdate(productDb,productDto);
      productRepository.save(updateProduct);
      ProductDto productDtoSend = productMapper.entityToDtoUpdateProduct(updateProduct);
      return ResponseEntity.status(HttpStatus.OK).body(productDtoSend);
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductDto(ERROR_INESPERADO + e.getMessage()));
    }
  }

  @Transactional
  public ResponseEntity<List<ProductImageDto>> updateImages(List<MultipartFile> images, Long productId) {
    try {
      List<ProductImage> productImages = productImageService.findByProductIdAndStatusTrue(productId);
      if(productImages.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new ProductImageDto("Imagenes no disponibles")));
      }
      Product product = productRepository.findByProductId(productId);
      for(int i = 0;i<images.size();i++){
        MultipartFile image = images.get(i);
        ProductImage productImage = productImages.get(i);
        String productName = product.getNombre().replace(" ","_");
        String imageName = productName + "_" + productImage.getId();
        String imageUrl = this.cloudinaryService.updateFile(image,imageName);
        productImage.setImageUrl(imageUrl);
        productImageService.save(productImage);
      }
      List<ProductImageDto> listProductImageDto= productImageMapper.entityListToDtoList(productImages);
      return ResponseEntity.status(HttpStatus.OK).body(listProductImageDto);
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(List.of(new ProductImageDto(ERROR_INESPERADO + e.getMessage())));
    }
  }

  public List<ProductImage> saveImageProduct(List<MultipartFile> images,Product product) throws IOException {
    List<ProductImage> productImages = new ArrayList<>();
    for (MultipartFile image : images) {
      ProductImage productImage = new ProductImage();
      productImage.setProduct(product.getId());
      productImageService.save(productImage);
      String productName = product.getNombre().replace(" ","_");
      String imageName = productName + "_" + productImage.getId();
      String imageUrl = cloudinaryService.uploadFile(image, imageName);
      productImage.setImageUrl(imageUrl);
      productImageService.save(productImage);
      productImages.add(productImage);
    }
    return productImages;
  }

  public ResponseEntity<List<ProductDto>> findAllProduct() {
    try {
      List<Product> listProduct = productRepository.findByStatusTrue();
      if(listProduct.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
      }
      List<ProductDto> listProductDto = productMapper.entityListToDtoList(listProduct);
      return ResponseEntity.status(HttpStatus.OK).body(listProductDto);
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(List.of());
    }
  }

  public ResponseEntity<Map<String,String>> deleteProduct(ProductDto productDto) {
    Map<String, String> response = new HashMap<>();
    try {
      Optional<Product> product = productRepository.findByNombreAndStatusTrue(productDto.getNombre());
      if(product.isEmpty()){
        response.put(MESSAGE,"Producto no encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }
      Product productDb = product.get();
      productDb.setStatus(false);
      productRepository.save(productDb);
      response.put(MESSAGE,"Producto eliminado con éxito");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }catch(Exception e){
      response.put(ERROR,"Error al eliminar el producto: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  public ResponseEntity<List<ProductDto>> findByFilters(ProductDto productDto) {
    try {
      List<Product> product = this.productRepository.findByTerm(productDto.getNombre(),productDto.getMarca(),productDto.getCategoria());
      if(product.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
      }
      List<ProductDto> productDtoBackend = this.productMapper.entityListToDtoList(product);
      return ResponseEntity.status(HttpStatus.OK).body(productDtoBackend);
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
    }
  }

  public ResponseEntity<List<ProductImageDto>> getProductImages(Long productId) {
    try {
      List<ProductImage> productImages = this.productImageService.findByProductIdAndStatusTrue(productId);
      if(productImages.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
      }
      List<ProductImageDto> productImagesDto = this.productImageMapper.entityListToDtoList(productImages);
      return ResponseEntity.status(HttpStatus.OK).body(productImagesDto);
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
    }
  }
}
