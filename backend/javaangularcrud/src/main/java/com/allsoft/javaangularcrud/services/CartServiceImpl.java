package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.*;
import com.allsoft.javaangularcrud.entity.*;
import com.allsoft.javaangularcrud.mapper.CartMapper;
import com.allsoft.javaangularcrud.repository.CartItemRepository;
import com.allsoft.javaangularcrud.repository.CartRepository;
import com.allsoft.javaangularcrud.repository.ProductRepository;
import com.allsoft.javaangularcrud.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

  private final UserRepository userRepository;
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final CartItemRepository cartItemRepository;
  private final CartMapper cartMapper;

  public CartServiceImpl(UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, CartMapper cartMapper) {
    this.userRepository = userRepository;
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
    this.cartItemRepository = cartItemRepository;
    this.cartMapper = cartMapper;
  }

  @Override
  public ResponseEntity<CartDto> getCart(UserDto userDto) {
    CartDto cartDto = new CartDto();
    try {
      Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
      if(optionalUser.isPresent()){
        User user = optionalUser.get();
        Cart cart = cartRepository.findById(user.getId()).orElse(new Cart());
        cartDto = this.cartMapper.entityToDto(cart);
        return ResponseEntity.status(HttpStatus.OK).body(cartDto);
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cartDto);
      }
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cartDto);
    }
  }

  @Override
  public ResponseEntity<CartDto> addProductToCart(UserDto userDto, Long productId, int cantidad) {
    try {
      Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
      if(optionalUser.isPresent()){
        User user = optionalUser.get();
        Cart cart = cartRepository.findById(user.getId()).orElse(new Cart());
        Optional<Product> optionalProduct = productRepository.findByIdAndStatusTrue(productId);
        if(optionalProduct.isPresent()){
          Product product = optionalProduct.get();
          Optional<CartItem> optionalCartItem = cart.getItems().stream()
                  .filter(item -> item.getProduct().getId().equals(productId))
                  .findFirst();
          CartItem cartItem;
          if(optionalCartItem.isPresent()){
            cartItem = optionalCartItem.get();
            cartItem.setCantidad(cartItem.getCantidad() + cantidad);
          }else{
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setCantidad(cantidad);
          }
          cart.getItems().add(cartItem);
          cartRepository.save(cart);
          CartDto cartDto = this.cartMapper.entityToDto(cart);
          return ResponseEntity.status(HttpStatus.OK).body(cartDto);
        }else{
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto());
        }
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto());
      }
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CartDto());
    }
  }

  @Override
  public ResponseEntity<CartDto> removeProductFromCart(UserDto userDto, Long productId) {
    try {
      Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
      if(optionalUser.isPresent()){
        User user = optionalUser.get();
        Cart cart = cartRepository.findById(user.getCart().getId()).orElse(new Cart());
        Optional<CartItem> optionalCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if(optionalCartItem.isPresent()){
          CartItem cartItem = optionalCartItem.get();
          cart.getItems().remove(cartItem);
          cartItemRepository.delete(cartItem);
        }
        cartRepository.save(cart);
        CartDto cartDto = cartMapper.entityToDto(cart);
        return ResponseEntity.status(HttpStatus.OK).body(cartDto);
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto());
      }
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CartDto());
    }
  }
}