package com.allsoft.javaangularcrud.controller;

import com.allsoft.javaangularcrud.dto.OrderDto;
import com.allsoft.javaangularcrud.dto.OrderRequest;
import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public ResponseEntity<List<OrderDto>> getAllOrders(){
    return orderService.getAllOrders();
  }

  @PostMapping("/user")
  public ResponseEntity<List<OrderDto>> getOrderByUser(@RequestBody OrderRequest orderRequest){
    return orderService.getOrderByUser(orderRequest);
  }

  @PostMapping("/product")
  public ResponseEntity<List<OrderDto>> getOrderByProduct(@RequestBody ProductDto productDto){
    return orderService.getOrderByProduct(productDto);
  }

  @PostMapping("/create")
  public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest){
    return orderService.createOrder(orderRequest);
  }
}
