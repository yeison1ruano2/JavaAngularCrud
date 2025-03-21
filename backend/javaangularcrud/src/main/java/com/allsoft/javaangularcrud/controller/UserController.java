package com.allsoft.javaangularcrud.controller;

import com.allsoft.javaangularcrud.dto.ProductDto;
import com.allsoft.javaangularcrud.dto.UserDto;
import com.allsoft.javaangularcrud.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PutMapping()
  public ResponseEntity<Map<String,String>> updateUser (
          @RequestHeader("Authorization") String authHeader,
          @RequestBody UserDto userDto
  ){
    return this.userService.updateUser(authHeader,userDto);
  }

  @PostMapping()
  public ResponseEntity<Map<String,String>> changePassword(
          @RequestHeader("Authorization") String authHeader,
          @RequestBody UserDto userDto
  ){
    return this.userService.changePassword(authHeader,userDto);
  }

  @GetMapping("/product")
  public ResponseEntity<List<ProductDto>> findAllProduct(){
    return ResponseEntity.ok(this.userService.findAllProduct());
  }

  @PostMapping("/search")
  public ResponseEntity<List<ProductDto>> findByTerm(@RequestBody ProductDto productDto){
    return ResponseEntity.ok(this.userService.findByTerm(productDto));
  }
}
