package com.allsoft.javaangularcrud.controller;

import com.allsoft.javaangularcrud.dto.AuthDto;
import com.allsoft.javaangularcrud.dto.UserDto;
import com.allsoft.javaangularcrud.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/authUser")
public class AuthUserController {

  private final UserService userService;

  public AuthUserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<Map<String,String>> registerUser(@RequestBody UserDto userDto) {
    return this.userService.registerUser(userDto);
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> loginUser(@RequestBody AuthDto authDto) {
    return this.userService.loginUser(authDto);
  }

}
