package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.entity.Product;
import com.allsoft.javaangularcrud.entity.ProductImage;
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
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService {
  private static final String NOEXISTE = " no existe";
  private static final String ELPRODUCTO = "El producto con el ID ";
  private static final String ERROR = "error";
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final ProductImageRepository productImageRepository;
  private final CloudinaryService cloudinaryService;
  private final ObjectMapper objectMapper;

  @Autowired
  public ProductService(ProductRepository productRepository, ProductMapper productMapper, ProductImageRepository productImageRepository, CloudinaryService cloudinaryService, ObjectMapper objectMapper) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
    this.productImageRepository = productImageRepository;
    this.cloudinaryService = cloudinaryService;
    this.objectMapper = objectMapper;
  }

  public ResponseEntity<Map<String,String>> saveProduct(String productDto, List<MultipartFile> images) {
    try {
      Product product = this.productMapper.dtoToEntity(objectMapper.readValue(productDto,ProductDto.class));
      this.productRepository.save(product);

      List<ProductImage> productImages = this.saveImageProduct(images,product);
      product.setImagenes(productImages);

      Map<String, String> response = new HashMap<>();
      response.put("message", "Producto creado con éxito");
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }catch (JsonProcessingException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(ERROR, "Error al procesar JSON: " + e.getMessage()));
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(ERROR, "Error al subir imágenes: " + e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(ERROR, "Error inesperado: " + e.getMessage()));
    }
  }

  public List<ProductImage> saveImageProduct(List<MultipartFile> images,Product product) throws IOException {
    List<ProductImage> productImages = new ArrayList<>();
    for (MultipartFile image : images) {
      ProductImage productImage = new ProductImage();
      productImage.setProduct(product);
      this.productImageRepository.save(productImage);
      String imageName = product.getNombre() + "_" + productImage.getId();
      String imageUrl = this.cloudinaryService.uploadFile(image, imageName);
      productImage.setImageUrl(imageUrl);
      this.productImageRepository.save(productImage);
      productImages.add(productImage);
    }
    return productImages;
  }

  public List<ProductDto> findAllProduct() {
    return this.productMapper.entityListToDtoList(this.productRepository.findByStatusTrue());
  }

  public ProductDto findById(Long id) {
    return productRepository.findByIdAndStatusTrue(id)
            .map(productMapper::entityToDto)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,ELPRODUCTO + id +  NOEXISTE));
  }

  public String updateProduct(Long id,ProductDto productDto) {
    Optional<Product> product=productRepository.findById(id);
    if(product.isPresent()){
      Product productDb=product.get();
      productRepository.save(this.productMapper.entityUpdate(productDb,productDto));
      return "Producto actualizado con éxito";
    }return ELPRODUCTO +id+ NOEXISTE;
  }

  public String deleteProduct(Long id) {
    Optional<Product> product = productRepository.findById(id);
    if(product.isPresent()){
      Product productDb = product.get();
      productDb.setStatus(false);
      productRepository.save(productDb);
      return "Producto eliminado con éxito";
    }return ELPRODUCTO +id+ NOEXISTE;
  }

  public List<ProductDto> findByFilters(ProductDto productDto) {
    return this.productMapper.entityListToDtoList(this.productRepository.findByTerm(productDto.nombre(),productDto.marca(),productDto.categoria()));
  }

  public List<ProductImage> getProductImages(Long productId) {
    return this.productImageRepository.findByProductId(productId);
  }
}
