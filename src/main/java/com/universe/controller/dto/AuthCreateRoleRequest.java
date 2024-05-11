package com.universe.controller.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import java.util.List;




/**
 * La anotación @Validated en la clase indica que esta clase debe ser validada en tiempo de ejecución.
 * Esto es útil cuando se utiliza en combinación con un sistema de validación, como el proporcionado por Spring Boot.
 * La anotación @Size(max = 3, message = "The user cannot have more than 3 roles") 
 * en el campo roleListName indica que la lista de roles no puede tener más de 3 elementos. 
 * Si se intenta crear un AuthCreateRoleRequest con una lista de más de 3 roles, 
 * se lanzará una excepción de validación con el mensaje "The user cannot have more than 3 roles
 */
@Validated
public record AuthCreateRoleRequest(
        @Size(max = 3, message = "The user cannot have more than 3 roles") List<String> roleListName) {
}
