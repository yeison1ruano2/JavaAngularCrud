package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.entity.Role;
import com.allsoft.javaangularcrud.entity.RoleName;
import com.allsoft.javaangularcrud.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
  private final RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role findByName(RoleName roleName) {
    return this.roleRepository.findByName(roleName);
  }
}
