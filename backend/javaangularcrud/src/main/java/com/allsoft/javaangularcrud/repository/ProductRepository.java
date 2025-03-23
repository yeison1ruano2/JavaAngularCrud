package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product,Long> {

  List<Product> findByStatusTrue();

  @Query("SELECT p FROM Product p Where p.id=:id")
  Product findByProductId(@Param("id")Long id);

  Optional<Product> findByIdAndStatusTrue(Long id);

  @Query("SELECT p FROM Product p " +
          "WHERE p.status = true " +
          "AND (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
          "AND (:marca IS NULL OR p.marca = :marca) " +
          "AND (:categoria IS NULL OR p.categoria = :categoria) " +
          "ORDER BY p.precio DESC")
  List<Product> findByTerm(@Param("nombre") String nombre, @Param("marca") String marca, @Param("categoria") String categoria);
}
