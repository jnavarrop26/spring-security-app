package com.universe.config.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.universe.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collection;

/**
 * Esta clase se encarga de validar el token JWT que se encuentra en el header de la petición
 * y de extraer la información necesaria para la autenticación del usuario.
 * @author Jose Navarro
 * @version 1.0.0
 */

public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {

        this.jwtUtils = jwtUtils;
    }

    /**
     * Este método se encarga de validar el token JWT que se encuentra en el header de la petición.
     * @author Jose Navarro
     * @param request Petición HTTP que se recibe para validar el token JWT.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        /**
         * Obtiene el token JWT del encabezado de autorización de la solicitud HTTP.
         */
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        /**
         * Si el  token no es nulo, entonces procede a validar y usar el token
         */
        if (jwtToken != null) {

            /**
             * Eliminia los primero 7 caracteres del token JWT. Esto se hace generalmente para elminiar el prefijo
             * "Bearer" que emilio el token JWT. en el encabezado de autorización.
             */
            jwtToken = jwtToken.substring(7);

            /**
             * Extrae el nombre de ususaio y los roles del token JWT decodificado
             */
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            /**
             * Extrae el nombre de usuario del token JWT decodificado y lo almacena en la variable "username".
             */
            String username = jwtUtils.extractUsername(decodedJWT);
            String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            /**
             * Convierte la lista de autoridades de una cadena separada por comas a una coleccion de Objetos GrantedAuthority.
             */
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            /**
             * Crea un nuevo contexto de seguridad vacio
             */
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            /**
             * Crea un nuevo objeto de autenticacion con el onmbre de usuaruio y las autoridades extraidas del token JWT
             */
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            /**
             * Establece el contecto de seguridad en el SecurityContextHolder,
             * que es una clase que almacena los detalles de la autenticacion.
             */
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);

        }
        /**
         * Continua con la cadena de filtros.
         * Esto permite que la solicitus continue a traves de otros filtros
         * de seguridad y finalmente llegue al controlador.
         */
        filterChain.doFilter(request, response);
    }
}
