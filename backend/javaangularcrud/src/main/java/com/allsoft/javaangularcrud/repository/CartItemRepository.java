package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
