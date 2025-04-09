package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.*;
import com.allsoft.javaangularcrud.entity.*;
import com.allsoft.javaangularcrud.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartMapper {

  private final ProductRepository productRepository;

  public CartMapper(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public CartDto entityToDto(Cart cart) {
    List<CartItemDto> cartItemDtos = cart.getItems().stream()
            .map(this::convertToDto)
            .toList();
    UserDto userDto = convertToDto(cart.getUser());
    return new CartDto(userDto, cartItemDtos);
  }

  public CartItemDto convertToDto(CartItem cartItem) {
    Optional<Product> optionalProduct = productRepository.findByNombreAndStatusTrue(cartItem.getProductId().toString());
    ProductDto productDto = convertToDto(optionalProduct.get());
    return new CartItemDto(productDto, cartItem.getCantidad());
  }

  public ProductDto convertToDto(Product product) {
    return new ProductDto(product.getNombre(), product.getDescripcion(), product.getPrecio(),
            product.getStock(), product.getCategoria(), product.getMarca(),product.getStatus());
  }

  public UserDto convertToDto(User user) {
    return new UserDto(user.getApellido(), user.getDireccion(), user.getEmail(), user.getNombre(),
            user.getNumCell(), user.getUsername());
  }
}
