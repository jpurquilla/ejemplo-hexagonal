package org.jpurquilla.domain.entity;

import org.jpurquilla.domain.exceptions.PersonaValidationException;
import org.jpurquilla.domain.vo.Sexo;

import java.util.Objects;

/**
 * Entity de Dominio: Persona
 *
 * Representa una persona según las reglas de negocio.
 *
 * ¿QUÉ TIENE?
 * - Campos: id, nombre, apellido, edad, sexo
 * - Constructores: vacío, con parámetros, completo
 * - Validación: método validar()
 * - Comportamiento: método actualizar()
 * - Getters y setters
 *
 * ¿QUÉ NO TIENE?
 * - ❌ @Entity (es JPA)
 * - ❌ @Table, @Column (es JPA)
 * - ❌ @GeneratedValue (es JPA)
 * - ❌ Ninguna anotación técnica
 *
 * ¿POR QUÉ sin anotaciones JPA?
 * Porque es DOMINIO PURO. Si mañana cambias de BD:
 * - H2 → PostgreSQL: solo cambias el adaptador
 * - REST → gRPC: solo cambias el controller
 * - La Persona del dominio NO cambia
 */
public class Persona {
    //Aqui van todos los atributos y value object
    //Recuerden que aqui se coloca el dominio que estan tratando con reglas de negocio.

    private Long id;
    private String nombre;
    private String apellido;
    private Sexo sexo;
    private Integer edad;

    public Persona() {
    }

    public Persona(String nombre, String apellido, Sexo sexo, Integer edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.edad = edad;
    }

    public Persona(Long id, String nombre, String apellido, Sexo sexo, Integer edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.edad = edad;
    }

    // ==================== VALIDACIÓN ====================

    /**
     * Valida todas las reglas de negocio de una Persona.
     *
     * IMPORTANTE: Esta es la única forma de garantizar que una Persona
     * siempre sea válida dentro del dominio.
     *
     * Se llama desde los Use Cases ANTES de guardar.
     *
     * Si hay error, lanza PersonaValidationException (excepción de dominio).
     * El GlobalExceptionHandler la captura y la convierte en HTTP 400.
     */
    public void validar() {
        // Validar nombre
        if (nombre == null || nombre.isBlank()) {
            throw new PersonaValidationException("nombre", "No puede estar vacío");
        }

        if (nombre.length() < 2) {
            throw new PersonaValidationException("nombre", "Debe tener al menos 2 caracteres");
        }

        // Validar apellido
        if (apellido == null || apellido.isBlank()) {
            throw new PersonaValidationException("apellido", "No puede estar vacío");
        }

        if (apellido.length() < 2) {
            throw new PersonaValidationException("apellido", "Debe tener al menos 2 caracteres");
        }

        // Validar edad
        if (edad == null) {
            throw new PersonaValidationException("edad", "No puede ser nula");
        }

        if (edad < 0 || edad > 150) {
            throw new PersonaValidationException("edad", "Debe estar entre 0 y 150");
        }

        // Validar sexo
        if (sexo == null) {
            throw new PersonaValidationException("sexo", "No puede ser nulo");
        }
    }

    // ==================== MÉTODOS DE NEGOCIO ====================

    /**
     * Actualiza los datos de una persona.
     *
     * Útil para el Use Case de modificación.
     * La validación la hace el Use Case DESPUÉS de llamar a este método.
     */
    public void actualizar(String nombre, String apellido, Integer edad, Sexo sexo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo = sexo;
        // La validación la hace el use case después
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", sexo=" + sexo +
                ", edad=" + edad +
                '}';
    }
}
