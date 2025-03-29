package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.OrderDto;
import com.allsoft.javaangularcrud.dto.OrderRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface OrderService {
  ResponseEntity<List<OrderDto>> getAllOrders();
  ResponseEntity<OrderDto> getOrderById(Long id);
  ResponseEntity<OrderDto> createOrder(OrderRequest orderRequest);
  ResponseEntity<OrderDto> updateOrder(Long id, OrderDto orderDto);
  ResponseEntity<Map<String, String>> deleteOrder(Long id);
}
