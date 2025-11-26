package org.jpurquilla.framework.adapters.output.mapper;

import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.domain.vo.Sexo;
import org.jpurquilla.framework.adapters.output.persistence.PersonaJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * MAPPER DE SALIDA: PersonaOutputMapper
 *
 * Convierte entre:
 * - Persona (dominio) ↔ PersonaJpaEntity (JPA)
 *
 * ¿Por qué existe este mapper?
 * - El dominio NO conoce JPA
 * - PersonaJpaEntity tiene @Entity, @Column, etc.
 * - El adaptador del repositorio hace la conversión automáticamente
 *
 * FLUJO:
 * 1. Use Case trabaja con Persona (dominio)
 * 2. PersonaRepositoryAdapter recibe Persona
 * 3. PersonaOutputMapper: Persona → PersonaJpaEntity
 * 4. Panache persiste PersonaJpaEntity
 * 5. Panache recupera PersonaJpaEntity
 * 6. PersonaOutputMapper: PersonaJpaEntity → Persona
 * 7. Use Case devuelve Persona
 *
 * CONVERSIONES:
 * 1. Persona { sexo: Sexo.MASCULINO } ↔ PersonaJpaEntity { sexo: "M" }
 */

@Mapper(componentModel="cdi")
public interface PersonaOutputMapper {

    /**
     * Convierte Persona de dominio a PersonaJpaEntity.
     *
     * Ejemplo:
     * Input:  Persona { id: 1, nombre: "Juan", sexo: Sexo.MASCULINO }
     * Output: PersonaJpaEntity { id: 1, nombre: "Juan", sexo: "M" }
     *
     * @param persona la persona de dominio
     * @return una entity de JPA
     */
    @Mapping(target = "sexo", source = "sexo", qualifiedByName = "sexoToString")
    PersonaJpaEntity toJpaEntity(Persona persona);

    /**
     * Convierte PersonaJpaEntity a Persona de dominio.
     *
     * Ejemplo:
     * Input:  PersonaJpaEntity { id: 1, nombre: "Juan", sexo: "M" }
     * Output: Persona { id: 1, nombre: "Juan", sexo: Sexo.MASCULINO }
     *
     * @param jpaEntity la entity de JPA
     * @return una persona de dominio
     */
    @Mapping(target = "sexo", source = "sexo", qualifiedByName = "stringToSexo")
    Persona toDomain(PersonaJpaEntity jpaEntity);

    /**
     * Convierte String a Sexo (Enum).
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
        return Sexo.fromCodigo(codigo);
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
        return sexo.getCodigo();
    }
}
