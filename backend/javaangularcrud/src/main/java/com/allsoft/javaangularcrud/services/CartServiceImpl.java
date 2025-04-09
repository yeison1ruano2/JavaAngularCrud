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

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

  private final UserRepository userRepository;
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final CartItemRepository cartItemRepository;
  private static final String USERNOTFOUND = "Usuario no encontrado";
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
    try {
      Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
      if(optionalUser.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto(USERNOTFOUND));
      }
      User user = optionalUser.get();
      Cart cart = cartRepository.findByUserId(user.getId()).orElse(new Cart());
      CartDto cartDto = cartMapper.entityToDto(cart);
      return ResponseEntity.status(HttpStatus.OK).body(cartDto);
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CartDto("Error al obtener el carrito"));
    }
  }

  @Override
  public ResponseEntity<CartDto> addProductToCart(CartRequest cartRequest) {
    try {
      Optional<User> optionalUser = userRepository.findByUsername(cartRequest.getUsername());
      if(optionalUser.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto(USERNOTFOUND));
      }
      User user = optionalUser.get();
      Cart cart = getOrCreateCart(user);
      Optional<Product> optionalProduct = productRepository.findByNombreAndStatusTrue(cartRequest.getNameProduct());
      if(optionalProduct.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto("El producto no existe"));
      }
      Product product = optionalProduct.get();
      if(cartRequest.getCantidad()<1 || cartRequest.getCantidad() > product.getStock()){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CartDto("Cantidad no Valida"));
      }
      addUpdateCartItem(cart,product,cartRequest.getCantidad());
      cartRepository.save(cart);
      CartDto cartDto = this.cartMapper.entityToDto(cart);
      return ResponseEntity.status(HttpStatus.OK).body(cartDto);
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CartDto("Error al agregar producto al carrito"));
    }
  }

  private void addUpdateCartItem(Cart cart, Product product, int cantidad) {
    Optional<CartItem> optionalCartItem = cart.getItems().stream()
            .filter(item -> item.getProductId().equals(product.getId()))
            .findFirst();
    CartItem cartItem;
    if(optionalCartItem.isPresent()){
      cartItem = optionalCartItem.get();
      int newQuantity = cartItem.getCantidad() + cantidad;
      if(newQuantity > product.getStock()){
        throw new IllegalArgumentException("Cantidad excede el stock disponible");
      }
      cartItem.setCantidad(newQuantity);
    }else{
      cartItem = new CartItem();
      cartItem.setCart(cart);
      cartItem.setProductId(product.getId());
      cartItem.setCantidad(cantidad);
      cart.getItems().add(cartItem);
    }
  }

  private Cart getOrCreateCart(User user) {
    return cartRepository.findByUserId(user.getId()).orElseGet(()->{
      Cart newCart = new Cart();
      newCart.setUser(user);
      newCart.setItems(new ArrayList<>());
      return newCart;
    });
  }

  @Override
  public ResponseEntity<CartDto> decreaseProductQuantityInCart(CartRequest cartRequest) {
    try {
      Optional<User> optionalUser = userRepository.findByUsername(cartRequest.getUsername());
      if(optionalUser.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto(USERNOTFOUND));
      }
      User user = optionalUser.get();
      Cart cart = getOrCreateCart(user);
      Optional<Product> optionalProduct = productRepository.findByNombreAndStatusTrue(cartRequest.getNameProduct());
      if(optionalProduct.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto("Producto no encontrado"));
      }
      Product product = optionalProduct.get();
      Optional<CartItem> optionalCartItem = mapCartItemCart(cart,product);
      if(optionalCartItem.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto("Producto no encontrado en el carrito"));
      }
      CartItem cartItem = optionalCartItem.get();
      mapQuantityCartItem(cartItem,cartRequest,cart);
      cartRepository.save(cart);
      CartDto cartDto = cartMapper.entityToDto(cart);
      return ResponseEntity.status(HttpStatus.OK).body(cartDto);
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CartDto("Error al disminuir la cantidad del producto en el carrito"));
    }
  }

  private void mapQuantityCartItem(CartItem cartItem, CartRequest cartRequest, Cart cart) {
    int newQuantity = cartItem.getCantidad()-cartRequest.getCantidad();
    if(newQuantity >0){
      cartItem.setCantidad(newQuantity);
    }else{
      cart.getItems().remove(cartItem);
    }
  }

  private Optional<CartItem> mapCartItemCart(Cart cart,Product product) {
    return cart.getItems().stream().filter(item -> item.getProductId().equals(product.getId()))
            .findFirst();
  }

  @Override
  public ResponseEntity<CartDto>  removeProductFromCart(CartRequest cartRequest) {
    try {
      Optional<User> optionalUser = userRepository.findByUsername(cartRequest.getUsername());
      if(optionalUser.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto(USERNOTFOUND));
      }

      User user = optionalUser.get();
      Cart cart = cartRepository.findById(user.getCart().getId()).orElse(new Cart());
      Optional<Product> optionalProduct = productRepository.findByNombreAndStatusTrue(cartRequest.getNameProduct());
      if(optionalProduct.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CartDto("Producto no encontrado"));
      }
      Optional<CartItem> optionalCartItem = mapCartItemCart(cart,optionalProduct.get());
      if(optionalCartItem.isPresent()){
          CartItem cartItem = optionalCartItem.get();
          cart.getItems().remove(cartItem);
          cartItemRepository.delete(cartItem);
      }
      cartRepository.save(cart);
      CartDto cartDto = cartMapper.entityToDto(cart);
      return ResponseEntity.status(HttpStatus.OK).body(cartDto);
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CartDto("Error al eliminar el producto del carrito"));
    }
  }
}