package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.*;
import com.allsoft.javaangularcrud.entity.Order;
import com.allsoft.javaangularcrud.entity.OrderItem;
import com.allsoft.javaangularcrud.entity.Product;
import com.allsoft.javaangularcrud.entity.User;
import com.allsoft.javaangularcrud.mapper.OrderItemMapper;
import com.allsoft.javaangularcrud.mapper.OrderMapper;
import com.allsoft.javaangularcrud.repository.OrderItemRepository;
import com.allsoft.javaangularcrud.repository.OrderRepository;
import com.allsoft.javaangularcrud.repository.ProductRepository;
import com.allsoft.javaangularcrud.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final OrderItemRepository orderItemRepository;
  private final OrderItemMapper orderItemMapper;

  public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
    this.orderRepository = orderRepository;
    this.orderMapper = orderMapper;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
    this.orderItemRepository = orderItemRepository;
    this.orderItemMapper = orderItemMapper;
  }

  @Override
  public ResponseEntity<List<OrderDto>> getAllOrders() {
    try{
      List<Order> orders = orderRepository.findAll();
      List<OrderDto> ordersDto =mapOrdersToDto(orders);
      return ResponseEntity.status(HttpStatus.OK).body(ordersDto);
    }catch (Exception e){
      return handleInternalServerError();
    }
  }

  private List<OrderDto> mapOrdersToDto(List<Order> orders) {
    return orders.stream()
            .map(orderMapper::entityToDto)
            .toList();
  }

  @Override
  public ResponseEntity<List<OrderDto>> getOrderByUser(OrderRequest orderRequest) {
    try {
      List<Order> orders =  orderRepository.findOrderByUser(orderRequest.getNombreUsuario());
      if(orders.isEmpty()){
        return handleNotFound("Usuario no encontrado");
      }
      List<OrderDto> listOrderDto = mapOrdersToDto(orders);
      return ResponseEntity.status(HttpStatus.OK).body(listOrderDto);
    }catch(Exception e){
      return handleInternalServerError();
    }
  }

  @Override
  public ResponseEntity<List<OrderDto>> getOrderByProduct(ProductDto productDto) {
    try {
      Optional<Product> optionalProduct =  productRepository.findByNombreAndStatusTrue(productDto.getNombre());
      if(optionalProduct.isEmpty()){
        return handleNotFound("producto no encontrado");
      }
      Product product = optionalProduct.get();
      List<OrderItem> orderItems =  orderItemRepository.findByProduct(product.getId());
      List<OrderDto> orderDtos = new ArrayList<>();
      for(OrderItem orderItem: orderItems){
        Order order = orderRepository.findOrderById(orderItem.getOrder().getId());
        OrderDto orderDto = orderMapper.mapOrderToDto(order,orderItem);
        orderDtos.add(orderDto);
      }
      return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
    }
  }

  @Override
  @Transactional
  public ResponseEntity<OrderDto> createOrder(OrderRequest orderRequest) {
    try {
      Order order = new Order();
      Optional<User> optionalUser = userRepository.findByUsername(orderRequest.getNombreUsuario());
      if(optionalUser.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderDto("Usuario no encontrado"));
      }
      User user = optionalUser.get();
      order = orderMapper.mapperOrderPreview(order,user,orderRequest.getTotalAmount());
      order = orderRepository.save(order);
      List<OrderItem> orderItems = createOrderItems(orderRequest.getItems(),order);
      Order orderSave = orderMapper.createEntity(orderItems,order);
      orderRepository.save(orderSave);
      OrderDto orderDto =  orderMapper.entityToDto(orderSave);
      return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OrderDto("Error guardando la orden"));
    }
  }

  private List<OrderItem> createOrderItems(List<OrderItemRequest> items,Order order) {
    List<OrderItem> orderItems = new ArrayList<>();
    for(OrderItemRequest item: items){
      Optional<Product> product = productRepository.findByNombreAndStatusTrue(item.getProductName());
      if(product.isEmpty()){
        return List.of();
      }
      OrderItem orderItem = orderItemMapper.createOrderItem(order,product.get(),item.getQuantity());
      orderItems.add(orderItem);
    }
    return orderItems;
  }

  private ResponseEntity<List<OrderDto>> handleInternalServerError() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
  }

  private ResponseEntity<List<OrderDto>> handleNotFound(String message) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new OrderDto(message)));
  }
}
