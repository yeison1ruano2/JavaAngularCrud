package com.allsoft.javaangularcrud.services;

public interface JwtService {
  String generateToken(String username);
  boolean validateToken(String token);
  String getUsernameFromToken(String token);
}
