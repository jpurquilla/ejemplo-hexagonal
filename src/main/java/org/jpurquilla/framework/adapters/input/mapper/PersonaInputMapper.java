package org.jpurquilla.framework.adapters.input.mapper;

import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.domain.vo.Sexo;
import org.jpurquilla.framework.adapters.input.dto.PersonaRequestDTO;
import org.jpurquilla.framework.adapters.input.dto.PersonaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


/**
 * MAPPER DE ENTRADA: PersonaInputMapper
 *
 * Convierte entre:
 * - PersonaRequestDTO (lo que llega del cliente) ↔ Persona (dominio)
 * - Persona (dominio) ↔ PersonaResponseDTO (lo que devolvemos)
 *
 * ¿Qué hace MapStruct?
 * - Lee esta interfaz con @Mapper
 * - Genera automáticamente la implementación en tiempo de compilación
 * - No hay reflexión, es rápido y type-safe
 *
 * ¿Por qué componentModel = "cdi"?
 * - Quarkus usa CDI (Context and Dependency Injection)
 * - Le dice a MapStruct que genere un bean de CDI
 * - Así puedes inyectarlo con @Inject
 *
 * ¿Por qué hay métodos con @Named?
 * - Porque la conversión de "M" ↔ Sexo no es automática
 * - MapStruct no sabe que "M" es Sexo.MASCULINO
 * - Tú defines cómo hacerlo en @Named("stringToSexo")
 *
 * CONVERSIONES:
 * 1. PersonaRequestDTO { sexo: "M" } → Persona { sexo: Sexo.MASCULINO }
 * 2. Persona { sexo: Sexo.MASCULINO } → PersonaResponseDTO { sexo: "M" }
 */
@Mapper(componentModel="cdi")
public interface PersonaInputMapper {
    /**
     * Convierte DTO de entrada a Persona de dominio.
     *
     * Ejemplo:
     * Input:  { nombre: "Juan", sexo: "M" }
     * Output: Persona { nombre: "Juan", sexo: Sexo.MASCULINO }
     *
     * @param dto el DTO que llega del cliente
     * @return una Persona de dominio
     */
    @Mapping(target = "sexo", source = "sexo", qualifiedByName = "stringToSexo")
    Persona toPersonaDomain(PersonaRequestDTO dto);

    /**
     * Convierte Persona de dominio a DTO de respuesta.
     *
     * Ejemplo:
     * Input:  Persona { id: 1, nombre: "Juan", sexo: Sexo.MASCULINO }
     * Output: { id: 1, nombre: "Juan", sexo: "M" }
     *
     * @param persona la persona de dominio
     * @return un DTO de respuesta
     */
    @Mapping(target = "sexo", source = "sexo", qualifiedByName = "sexoToString")
    PersonaResponseDTO toDtoResponse(Persona persona);

    /**
     * Convierte String a Sexo (Enum).
     *
     * ¿Por qué @Named?
     * - Porque MapStruct no sabe cómo convertir automáticamente
     * - Tú defines la lógica aquí
     * - MapStruct lo usa donde lo referencias con qualifiedByName
     *
     * ¿default?
     * - Significa que es una implementación concreta
     * - MapStruct la genera tal cual, sin generar su propia versión
     *
     * Ejemplo:
     * Input:  "M"
     * Output: Sexo.MASCULINO
     *
     * @param codigo "M" o "F"
     * @return Sexo.MASCULINO o Sexo.FEMENINO
     */
    @Named("stringToSexo")
    default Sexo stringToSexo(String codigo) {
        if (codigo == null) {
            return null;
        }
        return Sexo.fromCodigo(codigo);  // Usa el método del Enum
    }

    /**
     * Convierte Sexo (Enum) a String.
     *
     * Ejemplo:
     * Input:  Sexo.MASCULINO
     * Output: "M"
     *
     * @param sexo Sexo.MASCULINO o Sexo.FEMENINO
     * @return "M" o "F"
     */
    @Named("sexoToString")
    default String sexoToString(Sexo sexo) {
        if (sexo == null) {
            return null;
        }
        return sexo.getCodigo();  // Devuelve "M" o "F"
    }
}
