package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
