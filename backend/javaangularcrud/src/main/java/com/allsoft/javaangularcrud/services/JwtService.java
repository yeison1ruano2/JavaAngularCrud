package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.entity.RoleName;

import java.util.List;

public interface JwtService {
  String generateToken(String username, Long userId, List<RoleName> roles);
  boolean validateToken(String token);
  String getUsernameFromToken(String token);
  Long getUserIdFromToken(String token);
}
