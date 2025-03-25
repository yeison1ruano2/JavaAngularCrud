package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
