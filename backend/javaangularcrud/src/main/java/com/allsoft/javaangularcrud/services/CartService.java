package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.CartDto;
import com.allsoft.javaangularcrud.dto.CartRequest;
import com.allsoft.javaangularcrud.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
  ResponseEntity<CartDto> getCart(UserDto userDto);
  ResponseEntity<CartDto> addProductToCart(CartRequest cartRequest);
  ResponseEntity<CartDto> removeProductFromCart(CartRequest cartRequest);
  ResponseEntity<CartDto> decreaseProductQuantityInCart(CartRequest cartRequest);
}
