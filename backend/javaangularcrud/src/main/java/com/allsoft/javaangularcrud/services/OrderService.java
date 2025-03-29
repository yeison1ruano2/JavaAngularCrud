package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.OrderDto;
import com.allsoft.javaangularcrud.dto.OrderRequest;
import com.allsoft.javaangularcrud.dto.ProductDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface OrderService {
  ResponseEntity<List<OrderDto>> getAllOrders();
  ResponseEntity<List<OrderDto>> getOrderByUser(OrderRequest orderRequest);
  ResponseEntity<OrderDto> createOrder(OrderRequest orderRequest);
  ResponseEntity<OrderDto> updateOrder(Long id, OrderDto orderDto);
  ResponseEntity<Map<String, String>> deleteOrder(Long id);
  ResponseEntity<List<OrderDto>> getOrderByProduct(ProductDto productDto);
}
