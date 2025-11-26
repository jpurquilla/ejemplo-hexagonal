package org.jpurquilla.framework.adapters.input.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de SALIDA: PersonaResponseDTO
 *
 * Representa los datos que se devuelven al cliente REST.
 *
 * ¿Por qué es un DTO?
 * - El cliente recibe JSON, no objetos Java
 * - El DTO mapea Object → JSON
 * - Controla qué campos se exponen (ej: no expone campos internos)
 *
 * ¿Por qué no tiene validaciones?
 * - Es RESPUESTA, no ENTRADA
 * - Quien crea la respuesta (la aplicación) es responsable de datos válidos
 * - El cliente solo recibe y consume
 *
 * ¿Por qué tiene Lombok?
 * - Mismo motivo que PersonaRequestDTO
 * - Es un objeto TÉCNICO
 *
 * ¿Diferencia con PersonaRequestDTO?
 * - ResponseDTO TIENE id (se genera en la BD)
 * - ResponseDTO NO TIENE validaciones
 * - ResponseDTO tiene sexo como String (mismo que RequestDTO)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String sexo;
}
