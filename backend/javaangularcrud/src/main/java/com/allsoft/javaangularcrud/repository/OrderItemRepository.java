package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

  @Query("SELECT oi From OrderItem oi WHERE oi.product.id = :productId")
  List<OrderItem> findByProduct(@Param("productId") Long productId);
}
