package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order,Long> {

  @Query("SELECT o FROM Order o WHERE o.user.nombre = :username")
  List<Order> findOrderByUser(@Param("nombre") String nombre);

  Order findOrderById(Long id);
}
