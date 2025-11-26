package org.jpurquilla.application.port.output;

import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.domain.vo.Sexo;

import java.util.List;
import java.util.Optional;

/**
 * PUERTO DE SALIDA: PersonaRepository
 *
 * Define el contrato de PERSISTENCIA que necesita la aplicación.
 * ¿Qué servicios de BD necesito para funcionar?
 *
 * ¿Quién la implementa?
 * - PersonaRepositoryAdapter en framework/adapters/output/persistence/
 *
 * ¿Por qué está aquí en application?
 * - Porque es el CONTRATO (interfaz) que el dominio necesita
 * - La implementación (cómo persistir) está en framework
 * - Si mañana cambias de BD, solo cambias la implementación
 *
 * ¿Por qué recibe/devuelve Persona (dominio)?
 * - Los Use Cases trabajan con Persona de dominio
 * - El adaptador convierte: Persona ↔ PersonaJpaEntity
 * - El dominio nunca ve JPA

 */
public interface PersonaRepository {
    /**
     * Caso de Uso 1: Crear una Persona
     *
     * Persiste una persona en la BD.
     * La persona debe estar validada antes.
     *
     * @param persona la persona a guardar (sin ID, se genera)
     * @return la persona con el ID asignado por la BD
     */
    Persona save(Persona persona);

    /**
     * Caso de Uso 2: Buscar por ID
     *
     * Busca una persona en la BD por su ID.
     *
     * @param id el identificador
     * @return Optional con la persona si existe, vacío si no
     */
    Optional<Persona> findById(Long id);

    /**
     * Caso de Uso 3: Actualizar
     *
     * Actualiza una persona existente en la BD.
     *
     * @param persona la persona con datos actualizados (debe tener ID)
     * @return la persona después de actualizar
     */
    Persona update(Persona persona);

    /**
     * Caso de Uso 4: Obtener todas
     *
     * Obtiene todas las personas de la BD.
     *
     * @return lista de todas las personas
     */
    List<Persona> findAll();

    /**
     * Caso de Uso 5: Buscar por Sexo
     *
     * Obtiene todas las personas con un sexo específico.
     *
     * @param sexo el sexo a filtrar (MASCULINO o FEMENINO)
     * @return lista de personas con ese sexo
     */
    List<Persona> findBySexo(Sexo sexo);

    /**
     * Caso de Uso 6: Eliminar por ID
     *
     * Elimina una persona de la BD.
     *
     * @param id el identificador de la persona a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean deleteById(Long id);

    /**
     * Método auxiliar: Contar personas
     *
     * @return el total de personas en la BD
     */
    long count();
}
