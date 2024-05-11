package com.universe.controller;

import org.springframework.web.bind.annotation.*;



/**
 * Esta es una clase de controlador en una aplicación Spring Boot que maneja diferentes tipos de solicitudes HTTP.
 * (1) @RestController: Esta anotación indica que la clase es un controlador REST. Esto significa que los métodos de la clase manejarán las solicitudes HTTP y devolverán respuestas HTTP.
 * (2) @RequestMapping("/method"): Esta anotación indica que todas las rutas definidas en este controlador estarán prefijadas con "/method".
 * (3) @GetMapping("/get"): Esta anotación indica que el método `callGet` manejará las solicitudes GET a la ruta "/method/get". El método devuelve una cadena de texto "Method Called With GET".
 * (4) @PostMapping("/post"): Similar a `@GetMapping("/get")`, esta anotación indica que el método `callPost` manejará las solicitudes POST a la ruta "/method/post". El método devuelve una cadena de texto "Method Called With POST".
 * (5) @PutMapping("/put"): Esta anotación indica que el método `callPut` manejará las solicitudes PUT a la ruta "/method/put". El método devuelve una cadena de texto "Method Called With PUT".
 * (6) @DeleteMapping("/delete"): Esta anotación indica que el método `callDelete` manejará las solicitudes DELETE a la ruta "/method/delete". El método devuelve una cadena de texto "Method Called With DELETE".
 */
@RestController
@RequestMapping("/method")
public class TestController {

    @GetMapping("/get")
    public String callGet(){
        return "Method Called With GET";
    }

    @PostMapping("/post")
    public String callPost(){
        return "Method Called With POST";
    }

    @PutMapping("/put")
    public String callPut(){
        return "Method Called With PUT";
    }

    @DeleteMapping("/delete")
    public String callDelete(){
        return "Method Called With DELETE";
    }
}
