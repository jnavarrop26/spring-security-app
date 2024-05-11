package com.universe.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * La anotación @JsonPropertyOrder({"username", "message", "status", "jwt"}) 
 * se utiliza para especificar el orden en que las propiedades deben ser serializadas.
 * En este caso, cuando este objeto se convierte a JSON, las propiedades se ordenarán en este orden:
 * username, message, status, jwt.
 */
@JsonPropertyOrder({"username", "message", "status", "jwt"})
public record AuthResponse(
        /**
         * String username: Este campo representa el nombre de usuario.
        */
        String username,
        /**
         * String message: Este campo representa un mensaje, 
         * que podría ser utilizado para transmitir información adicional,
         * como el resultado de una operación.
         */
        String message,
        /**
         * Este campo representa un token JWT (JSON Web Token), 
         * que es un estándar para la creación de tokens de acceso que permiten la propagación de identidad y privilegios.
        */
        String jwt,
        /**
         * Este campo representa el estado de una operación, generalmente si fue exitosa o no.
        */
        Boolean status) {
}
