package com.universe.controller;

import com.universe.controller.dto.AuthCreateUserRequest;
import com.universe.controller.dto.AuthLoginRequest;
import com.universe.controller.dto.AuthResponse;
import com.universe.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * (1) @RestController: Esta anotación indica que la clase es un controlador REST. Esto significa que los métodos de la clase manejarán las solicitudes HTTP y devolverán respuestas HTTP.
 * (2) @RequestMapping("/auth"): Esta anotación indica que todas las rutas definidas en este controlador estarán prefijadas con "/auth".
 * (3) @Autowired private UserDetailServiceImpl userDetailService;: Esto inyecta una instancia de UserDetailServiceImpl en el controlador. 
 *      UserDetailServiceImpl es probablemente un servicio que maneja la lógica de negocio relacionada con los detalles del usuario.
 * (4) @PostMapping("/sign-up"): Esta anotación indica que el método register manejará las solicitudes POST a la ruta "/auth/sign-up".
 * (5) public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest userRequest): Este método toma un AuthCreateUserRequest como entrada, que es validado (@Valid) y deserializado del cuerpo de la solicitud HTTP (@RequestBody). 
 *     Luego, llama al método createUser del servicio userDetailService con el userRequest como argumento.
 *     Finalmente, devuelve una ResponseEntity con el AuthResponse devuelto por createUser y un código de estado HTTP de 201 (CREATED).
 * (6) @PostMapping("/log-in"): Similar a @PostMapping("/sign-up"), esta anotación indica que el método login manejará las solicitudes POST a la ruta "/auth/log-in".
 * (7) public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest): 
 *     Este método es similar al método register, pero toma un AuthLoginRequest como entrada y llama al método loginUser del servicio userDetailService. 
 *     Devuelve una ResponseEntity con el AuthResponse devuelto por loginUser y un código de estado HTTP de 200 (OK).
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.createUser(userRequest), HttpStatus.CREATED);
    }
    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

}
