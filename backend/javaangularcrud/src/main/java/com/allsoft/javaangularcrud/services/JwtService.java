package com.allsoft.javaangularcrud.services;

public interface JwtService {
  String generateToken(String username,String role);
  boolean validateToken(String token);
  String getUsernameFromToken(String token);
  String getRoleFromToken(String token);
}
