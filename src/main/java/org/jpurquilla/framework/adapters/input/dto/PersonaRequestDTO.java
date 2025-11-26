package org.jpurquilla.framework.adapters.input.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO de ENTRADA: PersonaRequestDTO
 *
 * Representa los datos que llegan del cliente REST para crear/actualizar una Persona.
 *
 * ¿Por qué es un DTO?
 * - Los clientes mandan JSON, no objetos Java
 * - El DTO mapea JSON → Object
 * - Tiene validaciones JSONB para filtrar datos inválidos
 * - No es lógica de negocio, es transferencia de datos
 *
 * ¿Por qué tiene Lombok?
 * - Es un objeto TÉCNICO, no de dominio
 * - Lombok genera getters, setters, constructores
 * - Reduce boilerplate (código repetitivo)
 *
 * ¿Por qué JSON tiene "sexo": "M" pero dominio tiene Sexo.MASCULINO?
 * - El cliente solo sabe que el sexo es "M" o "F"
 * - El mapper convertirá "M" → Sexo.MASCULINO
 * - El dominio trabaja con tipos seguros (Enum)
 *
 * VALIDACIONES:
 * Las anotaciones @NotBlank, @Min, @Max validan el formato básico.
 * Las validaciones de NEGOCIO las hace Persona.validar() en el dominio.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaRequestDTO {
    /**
     * Nombre de la persona.
     *
     * Validaciones:
     * - @NotBlank: No puede ser vacío (null o espacios)
     * - Validaciones de negocio (longitud mínima): En Persona.validar()
     */
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    /**
     * Apellido de la persona.
     *
     * Validaciones:
     * - @NotBlank: No puede ser vacío
     * - Validaciones de negocio: En Persona.validar()
     */
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    /**
     * Edad de la persona.
     *
     * Validaciones:
     * - @NotNull: No puede ser nula
     * - @Min(0): Mínimo 0
     * - @Max(150): Máximo 150
     * - Validaciones de negocio: En Persona.validar()
     */
    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 0, message = "La edad debe ser mayor a 0")
    @Max(value = 150, message = "La edad debe ser menor a 150")
    private Integer edad;

    /**
     * Sexo de la persona.
     *
     * Valores válidos: "M" (Masculino) o "F" (Femenino)
     *
     * Validaciones:
     * - @NotBlank: No puede ser vacío
     * - @Pattern: Solo acepta M o F (case-insensitive)
     * - Conversión: En PersonaInputMapper será convertido a Sexo.MASCULINO o Sexo.FEMENINO
     */
    @NotBlank(message = "El sexo no puede estar vacío")
    @Pattern(regexp = "[MF]", message = "Sexo debe ser M o F")
    private String sexo;

}
