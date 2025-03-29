package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.RoleName;
import com.allsoft.javaangularcrud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);

  @Query("SELECT r.name FROM User u JOIN u.roles r WHERE u.id = :userId ")
  List<RoleName> findRoleByUserId(@Param("userId") Long userId);
}
