package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.OrderDto;
import com.allsoft.javaangularcrud.entity.Order;
import com.allsoft.javaangularcrud.entity.OrderItem;
import com.allsoft.javaangularcrud.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMapper {

  private final OrderItemMapper orderItemMapper;

  public OrderMapper(OrderItemMapper orderItemMapper) {
    this.orderItemMapper = orderItemMapper;
  }


  public OrderDto entityToDto(Order order){
    /*OrderDto orderDto = new OrderDto();
    orderDto.setId(order.getId());
    orderDto.setItems(order.getItems().stream()
            .map(orderItemMapper::entityToDto)
            .toList());
    orderDto.setTotalAmount(order.getTotalAmount());
    orderDto.setStatus(order.getStatus());
    orderDto.setUsername(order.getUser().getNombre() + order.getUser().getApellido());
    return orderDto;*/
    return new OrderDto();
  }

  public Order dtoToEntity(OrderDto orderDto){
    Order order = new Order();
    order.setId(orderDto.getId());
    order.setItems(orderDto.getItems().stream()
            .map(orderItemMapper::dtoToEntity)
            .toList());
    order.setTotalAmount(orderDto.getTotalAmount());
    order.setStatus(orderDto.getStatus());
    return order;
  }

  public Order createEntity(List<OrderItem> orderItems,Order order) {
    order.setItems(orderItems);
    return order;
  }

  public Order mapperOrderPreview(Order order, User user, Double totalAmount) {
    order.setUser(user);
    order.setTotalAmount(totalAmount);
    order.setStatus("PAGADO");
    return order;
  }
}
