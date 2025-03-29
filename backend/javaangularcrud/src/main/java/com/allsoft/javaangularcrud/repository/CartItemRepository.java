package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
@EnableJpaRepositories
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id =:id")
  List<CartItem> findByCartId(@Param("id") Long id);
}
