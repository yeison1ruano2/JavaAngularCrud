package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
  Optional<Cart> findByUserId(Long id);
}
