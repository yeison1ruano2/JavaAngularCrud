package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.OrderDto;
import com.allsoft.javaangularcrud.dto.OrderItemDto;
import com.allsoft.javaangularcrud.dto.OrderItemRequest;
import com.allsoft.javaangularcrud.dto.OrderRequest;
import com.allsoft.javaangularcrud.entity.Order;
import com.allsoft.javaangularcrud.entity.OrderItem;
import com.allsoft.javaangularcrud.entity.Product;
import com.allsoft.javaangularcrud.entity.User;
import com.allsoft.javaangularcrud.mapper.OrderItemMapper;
import com.allsoft.javaangularcrud.mapper.OrderMapper;
import com.allsoft.javaangularcrud.repository.OrderRepository;
import com.allsoft.javaangularcrud.repository.ProductRepository;
import com.allsoft.javaangularcrud.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final OrderItemMapper orderItemMapper;

  public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository, ProductRepository productRepository, OrderItemMapper orderItemMapper) {
    this.orderRepository = orderRepository;
    this.orderMapper = orderMapper;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
    this.orderItemMapper = orderItemMapper;
  }

  @Override
  public ResponseEntity<List<OrderDto>> getAllOrders() {
    try{
      List<Order> orders = orderRepository.findAll();
      List<OrderDto> ordersDto = orders.stream()
              .map(orderMapper::entityToDto)
              .toList();
      return ResponseEntity.status(HttpStatus.OK).body(ordersDto);
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
    }
  }

  @Override
  public ResponseEntity<OrderDto> getOrderById(Long id) {
    try {
      Optional<Order> order = orderRepository.findById(id);
      if(order.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderDto("Orden no encontrada"));
      }
      OrderDto orderDto = orderMapper.entityToDto(order.get());
      return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OrderDto("Ocurrio un error, intentalo de nuevo"));
    }
  }

  @Override
  @Transactional
  public ResponseEntity<OrderDto> createOrder(OrderRequest orderRequest) {
    try {
      Order order = new Order();
      Optional<User> optionalUser = userRepository.findByUsername(orderRequest.getUsername());
      if(optionalUser.isEmpty()){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderDto("Usuario no encontrado"));
      }
      User user = optionalUser.get();
      order = orderMapper.mapperOrderPreview(order,user,orderRequest.getTotalAmount());
      order = orderRepository.save(order);
      List<OrderItem> orderItems = createOrderItems(orderRequest.getItems(),order);
      Order orderSave = orderMapper.createEntity(orderItems,order);
      orderRepository.save(orderSave);
      return ResponseEntity.status(HttpStatus.OK).body(new OrderDto());
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OrderDto("Error: " + e.getMessage()));
    }
  }

  private List<OrderItem> createOrderItems(List<OrderItemRequest> items,Order order) {
    List<OrderItem> orderItems = new ArrayList<>();
    for(OrderItemRequest item: items){
      Optional<Product> optionalProduct = productRepository.findByNombreAndStatusTrue(item.getProductName());
      Product product = optionalProduct.get();
      OrderItem orderItem = orderItemMapper.createOrderItem(order,product,item.getQuantity());
      orderItems.add(orderItem);
    }
    return orderItems;
  }

  @Override
  public ResponseEntity<OrderDto> updateOrder(Long id, OrderDto orderDto) {
    return null;
  }

  @Override
  public ResponseEntity<Map<String, String>> deleteOrder(Long id) {
    return null;
  }
}
