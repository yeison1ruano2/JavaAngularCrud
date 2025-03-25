package com.allsoft.javaangularcrud.services;

import com.allsoft.javaangularcrud.dto.AuthDto;
import com.allsoft.javaangularcrud.dto.UserDto;
import com.allsoft.javaangularcrud.entity.Role;
import com.allsoft.javaangularcrud.entity.RoleName;
import com.allsoft.javaangularcrud.entity.User;
import com.allsoft.javaangularcrud.mapper.UserMapper;
import com.allsoft.javaangularcrud.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final UserMapper userMapper;
  private static final String MESSAGE = "message";
  private static final String ERROR = "error";

  public UserServiceImpl(UserRepository userRepository, RoleService roleService,
                         PasswordEncoder passwordEncoder, JwtService jwtService,
                         UserMapper userMapper) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.userMapper = userMapper;
  }

  public ResponseEntity<Map<String,String>> registerUser(UserDto userDto) {
    Map<String, String> response = new HashMap<>();
    if(userRepository.existsByUsername(userDto.getUsername())){
      response.put(MESSAGE,"El usuario ya esta registrado");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    try {
      Role role = this.roleService.findByName(RoleName.CLIENTE);
      User user = userMapper.dtoToEntityCreate(userDto,role);
      this.userRepository.save(user);
      response.put(MESSAGE,"Usuario registrado con éxito");
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }catch (Exception e){
      response.put(MESSAGE,"Ocurrio un error al registrar el usuario " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @Override
  public ResponseEntity<Map<String, String>> registerSeller(UserDto userDto) {
    Map<String, String> response = new HashMap<>();
    if(userRepository.existsByUsername(userDto.getUsername())){
      response.put(MESSAGE,"El usuario ya esta registrado");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    try {
      Role role = this.roleService.findByName(RoleName.VENDEDOR);
      User user = userMapper.dtoToEntityCreate(userDto,role);
      this.userRepository.save(user);
      response.put(MESSAGE,"Usuario registrado con éxito");
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }catch (Exception e){
      response.put(MESSAGE,"Ocurrio un error al registrar el usuario " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  public ResponseEntity<Map<String, String>> loginUser(AuthDto authDto) {
    Map<String, String> response = new HashMap<>();
    Optional<User> user = userRepository.findByUsername(authDto.getUsername());
    if(user.isEmpty()){
      response.put(MESSAGE,"Usuario no registrado");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    if (passwordEncoder.matches(authDto.getPassword(), user.get().getPassword())) {
      String token = jwtService.generateToken(user.get().getUsername());
      response.put("token", token);
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.status(401).body(Map.of(ERROR, "Credenciales inválidas"));
    }
  }

  public ResponseEntity<Map<String, String>> updateUser(String authHeader, UserDto userDto) {
    Map<String, String> response = new HashMap<>();
    try {
      String token = authHeader.substring(7);
      String username = jwtService.getUsernameFromToken(token);
      User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
      if(!user.getUsername().equals(username)){
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"El nombre de usuario no coincide con el token");
      }
      User updateUser = this.userMapper.dtoToEntityUpdate(userDto,user);
      userRepository.save(updateUser);
      response.put(MESSAGE,"Usuario actualizado con éxito");
      return ResponseEntity.ok(response);
    }catch(Exception e){
      response.put(ERROR,"Ocurrio un error al actualizar el usuario: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  public ResponseEntity<Map<String, String>> changePassword(String authHeader, UserDto userDto) {
    Map<String, String> response = new HashMap<>();
    if(!userDto.getPassword().equals(userDto.getRePassword())){
      response.put(MESSAGE,"Contraseñas no coincide");
      return ResponseEntity.ok(response);
    }
    try {
      String token = authHeader.substring(7);
      String username = jwtService.getUsernameFromToken(token);
      User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
      if(!user.getUsername().equals(username)){
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"El username no coincide con el token");
      }
      User userChangePassword = this.userMapper.dtoToEntityChangePassword(user,userDto);
      this.userRepository.save(userChangePassword);
      response.put(MESSAGE,"Contraseña cambiada con éxito");
      return ResponseEntity.ok(response);
    }catch (Exception e){
      response.put(ERROR,"Ocurrio un error al cambiar la contraseña: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }
}
