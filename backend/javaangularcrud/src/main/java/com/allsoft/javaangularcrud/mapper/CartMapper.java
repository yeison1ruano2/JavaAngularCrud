package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.*;
import com.allsoft.javaangularcrud.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartMapper {

  public CartDto entityToDto(Cart cart) {
    Set<CartItemDto> cartItemDtos = cart.getItems().stream()
            .map(this::convertToDto)
            .collect(Collectors.toSet());
    UserDto userDto = convertToDto(cart.getUser());
    return new CartDto(userDto, cartItemDtos);
  }

  public CartItemDto convertToDto(CartItem cartItem) {
    ProductDto productDto = convertToDto(cartItem.getProduct());
    return new CartItemDto(productDto, cartItem.getCantidad());
  }

  public ProductDto convertToDto(Product product) {
    List<ProductImageDto> imageDtos = product.getImagenes().stream()
            .map(image -> new ProductImageDto(image.getImageUrl()))
            .toList();
    return new ProductDto(product.getNombre(), product.getDescripcion(), product.getPrecio(),
            product.getStock(), product.getCategoria(), product.getMarca(), imageDtos, product.getStatus());
  }

  public UserDto convertToDto(User user) {
    return new UserDto(user.getApellido(), user.getDireccion(), user.getEmail(), user.getNombre(),
            user.getNumCell(), user.getPassword(), "", user.getRoles()
            .stream()
            .map(Role::getName)
            .collect(Collectors.toSet()),
            user.getUsername());
  }
}
