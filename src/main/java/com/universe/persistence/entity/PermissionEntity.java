package com.universe.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 *@Setter: Esta es una anotación de Lombok que genera automáticamente los métodos setter para todos los campos de la clase.
 *@Getter: Esta es una anotación de Lombok que genera automáticamente los métodos getter para todos los campos de la clase.
 *@Builder: Esta es una anotación de Lombok que proporciona un patrón de diseño de builder para la clase.
 * Esto significa que puedes crear instancias de PermissionEntity usando un patrón de builder.
 *@AllArgsConstructor: Esta es una anotación de Lombok que genera automáticamente un constructor con un parámetro para cada campo en la clase.
 *@NoArgsConstructor: Esta es una anotación de Lombok que genera automáticamente un constructor sin argumentos.
 *@Entity: Esta es una anotación de JPA que indica que esta clase es una entidad. Esto significa que se puede mapear a una tabla en la base de datos.
 *@Table(name = "permissions"): Esta es una anotación de JPA que se utiliza para especificar detalles de la tabla a la que se mapeará la entidad. En este caso, la entidad se mapeará a la tabla permissions.
 *@Id: Esta es una anotación de JPA que se utiliza para definir el campo que actúa como la clave primaria de la entidad.
 *@GeneratedValue(strategy = GenerationType.IDENTITY): Esta es una anotación de JPA que se utiliza para especificar cómo se generan los valores para el campo de la clave primaria. En este caso, se utiliza la estrategia IDENTITY, lo que significa que la base de datos genera automáticamente los valores.
 *@Column(unique = true, nullable = false, updatable = false): Esta es una anotación de JPA que se utiliza para especificar detalles de la columna a la que se mapeará el campo. En este caso, la columna es única (no puede tener valores duplicados), no puede ser nula y no se puede actualizar.
 *Private Long id;: Este es el campo que se mapea a la clave primaria de la entidad.
 *private String name;: Este es el campo que se mapea a la columna name de la entidad.
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String name;
}
