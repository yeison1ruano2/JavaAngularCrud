package com.allsoft.javaangularcrud.controller;

import com.allsoft.javaangularcrud.dto.UserDto;
import com.allsoft.javaangularcrud.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/authSeller")
public class AuthSellerController {

  private final UserService userService;

  public AuthSellerController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<Map<String,String>> registerSeller(@RequestBody UserDto userDto){
   return this.userService.registerSeller(userDto);
  }
}
