package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.entity.Role;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.List;


@Service
public class JwtServiceImpl implements JwtService{
  private static final long EXPIRATION_TIME = 3600000;
  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  public String generateToken(String username,String role) {
    return Jwts.builder()
            .setSubject(username)
            .claim("role",role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key)
            .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  @Override
  public String getRoleFromToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("role", String.class);
  }
}
