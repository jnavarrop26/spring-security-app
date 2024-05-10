package com.universe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;




/**
 * @SpringBootTest: Esta anotación indica que la clase es una prueba que debe cargar el contexto completo de la aplicación Spring Boot. 
 * Esto significa que se iniciarán todos los componentes de la aplicación, incluyendo controladores, servicios, repositorios, etc
 * (1) class SpringTokensUniverseApplicationTests: Esta es la declaración de la clase de prueba. 
 * El nombre de la clase sugiere que esta prueba está destinada a probar la aplicación SpringTokensUniverse.
 * (2) @Test: Esta anotación indica que el método siguiente es un método de prueba que debe ser ejecutado por el marco de pruebas.
 * (3)void contextLoads() {}: Este es un método de prueba que simplemente verifica que el contexto de la aplicación Spring Boot se carga correctamente.
 *  Si el contexto no se puede cargar por alguna razón (por ejemplo, si hay un error en la configuración de la aplicación), esta prueba fallará.
 */
@SpringBootTest
class SpringTokensUniverseApplicationTests {

	@Test
	void contextLoads() {
	}

}
