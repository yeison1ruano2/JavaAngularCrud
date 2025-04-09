package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.OrderItemDto;
import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.entity.Order;
import com.allsoft.javaangularcrud.entity.OrderItem;
import com.allsoft.javaangularcrud.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemMapper {
  private final ProductMapper productMapper;

  public OrderItemMapper(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  public OrderItem dtoToEntity(OrderItemDto orderItemDto){
    OrderItem orderItem = new OrderItem();
    orderItem.setPrice(orderItemDto.getPrice());
    orderItem.setQuantity(orderItemDto.getQuantity());
    return orderItem;
  }

  public OrderItem createOrderItem(Order order, Product product, Integer quantity) {
    OrderItem orderItem = new OrderItem();
    orderItem.setOrder(order);
    orderItem.setProductId(product.getId());
    orderItem.setPrice(product.getPrecio());
    orderItem.setQuantity(quantity);
    return orderItem;
  }

  public OrderItemDto entityToDto(OrderItem orderItem) {
    OrderItemDto orderItemDto = new OrderItemDto();
    orderItemDto.setProductDto(productMapper.onlyEntityToDto(orderItem.getProductId()));
    orderItemDto.setQuantity(orderItem.getQuantity());
    orderItemDto.setPrice(orderItem.getPrice());
    return orderItemDto;
  }

  public List<OrderItemDto> listEntityToDtoList(List<OrderItem> listOrderItemProduct) {
    return listOrderItemProduct.stream()
            .map(orderItem -> {
              ProductDto productDto = productMapper.onlyEntityToDto(orderItem.getProductId());
              OrderItemDto orderItemDto = new OrderItemDto();
              orderItemDto.setProductDto(productDto);
              orderItemDto.setQuantity(orderItem.getQuantity());
              orderItemDto.setPrice(orderItem.getPrice());
              return orderItemDto;
            }).toList();
  }
}
