package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.AuthDto;
import com.allsoft.javaangularcrud.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {

  ResponseEntity<Map<String,String>> registerUser(UserDto userDto);
  ResponseEntity<Map<String,String>> loginUser(AuthDto authDto);
  ResponseEntity<Map<String,String>> updateUser(String authHeader,UserDto userDto);
  ResponseEntity<Map<String,String>> changePassword(String authHeader,UserDto userDto);
  ResponseEntity<Map<String, String>> registerSeller(UserDto userDto);
}
