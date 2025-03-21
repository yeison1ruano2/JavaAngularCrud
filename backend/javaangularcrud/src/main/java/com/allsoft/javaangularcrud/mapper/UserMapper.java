package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.UserDto;
import com.allsoft.javaangularcrud.entity.Role;
import com.allsoft.javaangularcrud.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserMapper {
  private final PasswordEncoder passwordEncoder;

  public UserMapper(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public User dtoToEntityCreate(UserDto userDto, Role role){
    return new User(
            userDto.nombre(),
            userDto.apellido(),
            userDto.email(),
            userDto.direccion(),
            userDto.numCell(),
            userDto.username(),
            passwordEncoder.encode(userDto.password()),
            Collections.singleton(role)
    );
  }

  public User dtoToEntityUpdate(UserDto userDto, User user) {
    user.setNombre(userDto.nombre());
    user.setApellido(userDto.apellido());
    user.setDireccion(userDto.direccion());
    user.setEmail(userDto.email());
    user.setNumCell(userDto.numCell());
    return user;
  }

  public User dtoToEntityChangePassword(User user, UserDto userDto) {
    user.setPassword(userDto.password());
    return user;
  }
}
