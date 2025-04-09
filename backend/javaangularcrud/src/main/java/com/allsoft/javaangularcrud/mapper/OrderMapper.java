package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.OrderDto;
import com.allsoft.javaangularcrud.dto.OrderItemDto;
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
    OrderDto orderDto = new OrderDto();
    orderDto.setItems(order.getItems().stream()
            .map(orderItemMapper::entityToDto)
            .toList());
    orderDto.setTotalAmount(order.getTotalAmount());
    orderDto.setStatus(order.getStatus());
    orderDto.setUsername(order.getUser().getUsername());
    return orderDto;
  }

  /*public Order dtoToEntity(OrderDto orderDto){
    Order order = new Order();
    order.setId(orderDto.getId());
    order.setItems(orderDto.getItems().stream()
            .map(orderItemMapper::dtoToEntity)
            .toList());
    order.setTotalAmount(orderDto.getTotalAmount());
    order.setStatus(orderDto.getStatus());
    return order;
  }*/

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


  public List<OrderDto> entityListToDtoList(List<Order> listOrderUser) {
    return listOrderUser.stream()
            .map(this::entityToDto)
            .toList();
  }

  public List<OrderDto> entityListToDtoListOrderItem(List<Order> listOrder, List<OrderItemDto> listOrderItemDto) {
    return listOrder.stream()
            .map(order->{
              OrderDto orderDto = new OrderDto();
              orderDto.setItems(listOrderItemDto);
              orderDto.setTotalAmount(order.getTotalAmount());
              orderDto.setStatus(order.getStatus());
              orderDto.setUsername(order.getUser().getUsername());
              return orderDto;
            }).toList();
  }

  public OrderDto mapOrderToDto(Order order, OrderItem orderItem) {
    OrderItemDto orderItemDto = orderItemMapper.entityToDto(orderItem);
    return new OrderDto(
            List.of(orderItemDto),
            order.getStatus(),
            order.getTotalAmount(),
            order.getUser().getUsername(),
            null
    );
  }
}
