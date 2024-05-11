package com.universe.controller.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * (1) @NotBlank String username: Este campo representa el nombre de usuario. 
 * La anotación @NotBlank indica que este campo no puede ser nulo y 
 * debe contener al menos un carácter que no sea un espacio en blanco.
 * (2) @NotBlank String password: Este campo representa la contraseña del usuario. 
 * Al igual que el campo username, la anotación @NotBlank indica que este campo no puede ser nulo 
 * y debe contener al menos un carácter que no sea un espacio en blanco.
 */
public record AuthLoginRequest(@NotBlank String username,
                               @NotBlank String password) {
}
