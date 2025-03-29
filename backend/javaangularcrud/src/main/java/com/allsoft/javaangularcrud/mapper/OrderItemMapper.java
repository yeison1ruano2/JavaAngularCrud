package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.OrderItemDto;
import com.allsoft.javaangularcrud.entity.Order;
import com.allsoft.javaangularcrud.entity.OrderItem;
import com.allsoft.javaangularcrud.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class OrderItemMapper {

 /* public OrderItemDto entityToDto(OrderItem orderItem){
    OrderItemDto orderDto = new OrderItemDto();
    orderDto.setProductName(orderItem.getProduct().getNombre());
    orderDto.setProductName(orderItem.getProduct().getNombre());
    orderDto.setPrice(orderItem.getPrice());
    orderDto.setQuantity(orderItem.getQuantity());
    return orderDto;
  }*/

  public OrderItem dtoToEntity(OrderItemDto orderItemDto){
    OrderItem orderItem = new OrderItem();
    orderItem.setPrice(orderItemDto.getPrice());
    orderItem.setQuantity(orderItemDto.getQuantity());
    return orderItem;
  }

  public OrderItem createOrderItem(Order order, Product product, Integer quantity) {
    OrderItem orderItem = new OrderItem();
    orderItem.setOrder(order);
    orderItem.setProduct(product);
    orderItem.setPrice(product.getPrecio());
    orderItem.setQuantity(quantity);
    return orderItem;
  }
}
