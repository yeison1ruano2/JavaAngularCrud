package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.entity.Role;
import com.allsoft.javaangularcrud.entity.RoleName;

public interface RoleService {
  Role findByName(RoleName roleName);
}
