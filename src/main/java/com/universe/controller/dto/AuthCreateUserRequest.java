package com.universe.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * (1) @NotBlank String username: Este campo representa el nombre de usuario.
 * La anotación @NotBlank indica que este campo no puede ser nulo y
 * debe contener al menos un carácter que no sea un espacio en blanco.
 * (2) @NotBlank String password: Este campo representa la contraseña del usuario. 
 * Al igual que el campo username, la anotación @NotBlank indica que este campo no puede ser nulo 
 * y debe contener al menos un carácter que no sea un espacio en blanco.
 * (3) @Valid AuthCreateRoleRequest roleRequest: Este campo es un objeto AuthCreateRoleRequest, 
 * que como discutimos anteriormente, representa una lista de roles que se asignarán a un usuario.
 * La anotación @Valid indica que este objeto debe ser validado cuando se valida el objeto AuthCreateUserRequest.
 * Esto significa que si se intenta crear un AuthCreateUserRequest con un AuthCreateRoleRequest inválido (por ejemplo, con más de 3 roles),
 * se lanzará una excepción de validación.
 */
public record AuthCreateUserRequest(@NotBlank String username,
                                    @NotBlank String password,
                                    @Valid AuthCreateRoleRequest roleRequest) {
}
