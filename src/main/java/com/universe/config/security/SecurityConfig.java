package com.universe.config.security;

import com.universe.config.security.filter.JwtTokenValidator;
import com.universe.service.UserDetailServiceImpl;
import com.universe.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.Arrays;
import java.util.Collections;


/**
 * Esta clase se encarga de configurar la seguridad de la aplicación para proteger los EndPoints
 * y permitir el acceso a los usuarios con los roles adecuados ademas de configurar el filtro
 * que valida el token JWT.
 * @author Jose Navarro
 * @version 1.0.0
 */


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;
    
    /**
     * Este método securityFilterChain es una configuración de seguridad en una aplicación Spring Boot. Aquí está lo que hace cada parte:
     * (1) @Bean: Esta anotación indica que el método produce un bean que será gestionado por el contenedor de Spring.
     * (2) SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception: Este método devuelve un objeto SecurityFilterChain que es una cadena de filtros de seguridad. 
     *      Toma dos parámetros: un objeto HttpSecurity que es una clase de configuración de seguridad y un AuthenticationProvider que es una interfaz para autenticar al usuario.
     * (3) httpSecurity.csrf(csrf -> csrf.disable()): Desactiva la protección CSRF (Cross-Site Request Forgery). 
     *      Esto puede ser necesario para ciertas APIs.
     * (4) sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)): 
     *      Configura la política de creación de sesiones para ser sin estado. Esto significa que la aplicación no mantendrá ninguna información de sesión entre las solicitudes.
     * (5) authorizeHttpRequests(http -> {...}): Configura las autorizaciones para las solicitudes HTTP.
     *     En este caso, se están configurando varios endpoints para que sean accesibles solo por usuarios con ciertos permisos.
     *     Por ejemplo, http.requestMatchers(HttpMethod.GET, "/method/get").hasAuthority("READ") significa que solo los usuarios con la autoridad "READ" pueden hacer solicitudes GET a "/method/get".
     * (6) http.anyRequest().denyAll(): 
     *     Esto significa que cualquier solicitud que no coincida con las anteriores será denegada.
     * (7) addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class):
     *    Añade un filtro personalizado JwtTokenValidator antes del filtro BasicAuthenticationFilter. Este filtro personalizado probablemente verifica los tokens JWT en las solicitudes.
     * (8) build(): Construye y devuelve el objeto SecurityFilterChain.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    // EndPoints publicos
                    http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();

                    // EndPoints Privados
                    http.requestMatchers(HttpMethod.GET, "/method/get").hasAuthority("READ");
                    http.requestMatchers(HttpMethod.POST, "/method/post").hasAuthority("CREATE");
                    http.requestMatchers(HttpMethod.DELETE, "/method/delete").hasAuthority("DELETE");
                    http.requestMatchers(HttpMethod.PUT, "/method/put").hasAuthority("UPDATE");

                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    /**
     * (1) public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception:
     * Este método devuelve un objeto AuthenticationManager que es una interfaz central en el sistema de seguridad de Spring. 
     * El AuthenticationManager es responsable de pasar las solicitudes de autenticación a un conjunto de AuthenticationProviders. 
     * Toma un parámetro: un objeto AuthenticationConfiguration que es una clase de configuración de autenticación.
     * (2) return authenticationConfiguration.getAuthenticationManager();:
     * Este método devuelve el AuthenticationManager de la configuración de autenticación proporcionada.
     * Esto significa que la configuración de autenticación específica utilizada en la aplicación se puede inyectar en este método, 
     * y este método simplemente devolverá el AuthenticationManager asociado con esa configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * (1) public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService):
     * Este método devuelve un objeto AuthenticationProvider que es una interfaz para autenticar al usuario.
     * Toma un parámetro: un objeto UserDetailServiceImpl que es una clase de servicio que implementa la interfaz UserDetailsService de Spring Security.
     * (2) DaoAuthenticationProvider provider = new DaoAuthenticationProvider();:
     * Crea un objeto DaoAuthenticationProvider que es una implementación de AuthenticationProvider.
     * (3) provider.setPasswordEncoder(passwordEncoder());:
     * Configura el PasswordEncoder para el proveedor de autenticación.
     * (4) provider.setUserDetailsService(userDetailService);:
     * Configura el UserDetailsService para el proveedor de autenticación.
     * (5) return provider;:
     * Devuelve el proveedor de autenticación configurado.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    /**
     * (1) public PasswordEncoder passwordEncoder(): 
     * Este método devuelve un objeto PasswordEncoder que es una interfaz para codificar contraseñas. 
     * En Spring Security, se utiliza para codificar (o "hash") las contraseñas antes de almacenarlas y para comparar las contraseñas proporcionadas por el usuario con las contraseñas almacenadas
     * (2) return new BCryptPasswordEncoder();:
     * Este método devuelve una nueva instancia de BCryptPasswordEncoder, 
     * que es una implementación de PasswordEncoder que utiliza el algoritmo BCrypt para codificar las contraseñas. 
     * BCrypt es un algoritmo de hash fuerte para contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
