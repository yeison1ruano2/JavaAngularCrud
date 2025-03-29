package com.allsoft.javaangularcrud.controller;

import com.allsoft.javaangularcrud.dto.CartDto;
import com.allsoft.javaangularcrud.dto.CartRequest;
import com.allsoft.javaangularcrud.dto.UserDto;
import com.allsoft.javaangularcrud.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping("/get")
  public ResponseEntity<CartDto> getCart(@RequestBody UserDto userDto){
    return cartService.getCart(userDto);
  }

  @PostMapping("/add")
  public ResponseEntity<CartDto> addProductToCart(@RequestBody CartRequest cartRequest)
  {
    return cartService.addProductToCart(cartRequest);
  }

  @PostMapping("/decrease")
  public ResponseEntity<CartDto> decreaseProductQuantityInCart(@RequestBody CartRequest cartRequest)
  {
    return cartService.decreaseProductQuantityInCart(cartRequest);
  }

  @DeleteMapping("/remove")
  public ResponseEntity<CartDto> removeProductFromCart(@RequestBody CartRequest cartRequest){
    return cartService.removeProductFromCart(cartRequest);
  }
}
