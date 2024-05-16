package com.universe.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Esta clase JwtUtils es un componente de Spring que proporciona utilidades para trabajar con tokens JWT (JSON Web Tokens).
 * Aquí está el desglose de los métodos:
 * createToken(Authentication authentication): Este método crea un token JWT.
     * Toma un objeto Authentication como argumento, que representa la autenticación del usuario actual.
     * El método utiliza la biblioteca com.auth0.jwt para crear el token.
     * El token incluye varios detalles, como el emisor, el sujeto (el nombre de usuario del usuario autenticado),
     * las autoridades del usuario (los roles o permisos del usuario), la fecha de emisión, la fecha de expiración, un ID de token y la fecha a partir de la cual el token es válido. El token se firma utilizando un algoritmo HMAC256 y la clave privada proporcionada.
 * validateToken(String token): Este método valida un token JWT.
     * Toma el token como argumento y utiliza un verificador JWT para verificar el token.
     * Si el token es válido, el método devuelve un objeto DecodedJWT que representa el token decodificado.
     * Si el token no es válido, el método lanza una excepción JWTVerificationException.
 * extractUsername(DecodedJWT decodedJWT): Este método extrae el nombre de usuario del token JWT decodificado.
     * Toma un objeto DecodedJWT como argumento y devuelve el sujeto del token, que es el nombre de usuario del usuario autenticado.
 * getSpecificClaim(DecodedJWT decodedJWT, String claimName): Este método obtiene una reclamación específica del token JWT decodificado. Toma un objeto DecodedJWT y el nombre de la reclamación como argumentos, y devuelve la reclamación del token.
     * La clase JwtUtils también tiene dos campos que se inyectan desde el entorno de Spring:
     * privateKey: Esta es la clave privada utilizada para firmar los tokens JWT. Se inyecta desde las propiedades de la aplicación (security.jwt.key.private).
     * userGenerator: Este es el emisor de los tokens JWT. Se inyecta desde las propiedades de la aplicación (security.jwt.user.generator).
 */


@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
        return jwtToken;
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalid, not Authorized");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }
}
