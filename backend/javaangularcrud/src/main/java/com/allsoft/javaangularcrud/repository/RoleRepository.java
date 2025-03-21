package com.allsoft.javaangularcrud.repository;

import com.allsoft.javaangularcrud.entity.Role;
import com.allsoft.javaangularcrud.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role,Long> {
  Role findByName(RoleName name);
}
