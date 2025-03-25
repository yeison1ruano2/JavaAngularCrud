package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.CartDto;
import com.allsoft.javaangularcrud.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
  ResponseEntity<CartDto> getCart(UserDto userDto);
  ResponseEntity<CartDto> addProductToCart(UserDto userDto, Long productId, int cantidad);
  ResponseEntity<CartDto> removeProductFromCart(UserDto userDto, Long productId);
}
