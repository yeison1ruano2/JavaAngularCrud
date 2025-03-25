package com.allsoft.javaangularcrud.mapper;

import com.allsoft.javaangularcrud.dto.UserDto;
import com.allsoft.javaangularcrud.entity.Cart;
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

  public User dtoToEntityCreate(UserDto userDto,Role role){
    return new User(
            userDto.getNombre(),
            userDto.getApellido(),
            userDto.getEmail(),
            userDto.getDireccion(),
            userDto.getNumCell(),
            userDto.getUsername(),
            passwordEncoder.encode(userDto.getPassword()),
            Collections.singleton(role),
            new Cart()
    );
  }

  public User dtoToEntityUpdate(UserDto userDto, User user) {
    user.setNombre(userDto.getNombre());
    user.setApellido(userDto.getApellido());
    user.setDireccion(userDto.getDireccion());
    user.setEmail(userDto.getEmail());
    user.setNumCell(userDto.getNumCell());
    return user;
  }

  public User dtoToEntityChangePassword(User user, UserDto userDto) {
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    return user;
  }
}
