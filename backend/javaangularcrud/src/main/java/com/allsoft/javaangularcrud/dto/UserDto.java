package com.allsoft.javaangularcrud.dto;

public record UserDto(
        String nombre,
        String apellido,
        String email,
        String direccion,
        String numCell,
        String username,
        String password,
        String rePassword
) {
}
