package com.allsoft.javaangularcrud.controller;

import com.allsoft.javaangularcrud.dto.OrderDto;
import com.allsoft.javaangularcrud.dto.OrderRequest;
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

  @GetMapping("/{id}")
  public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id){
    return orderService.getOrderById(id);
  }

  @PostMapping
  public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest){
    return orderService.createOrder(orderRequest);
  }
}
