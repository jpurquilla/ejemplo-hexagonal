package org.jpurquilla.application.port.input;

import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.framework.adapters.input.dto.PersonaRequestDTO;
import org.jpurquilla.framework.adapters.input.dto.PersonaResponseDTO;

import java.util.List;

/**
 * PUERTO DE ENTRADA: PersonaInputPort
 *
 * Define el contrato de ENTRADA a la aplicación.
 * ¿Qué operaciones puedo hacer desde el exterior?
 *
 * ¿Quién la implementa?
 * - PersonaController en framework/adapters/input/controller/
 *
 * ¿Por qué está en application/ports y no en framework?
 * - Define el CONTRATO (qué hace la aplicación)
 * - La implementación (cómo lo hace con REST) está en framework
 * - Podría haber otra implementación en CLI, gRPC, etc.
 *
 * ¿Por qué recibe/devuelve DTOs?
 * - Los DTOs son objetos de transferencia de datos
 * - El dominio no se expone al exterior
 * - El controller mapea DTO → Dominio → DTO
 */
public interface PersonaInputPort {
    /**
     * Caso de Uso 1: Crear una Persona
     *
     * @param persona la persona de dominio completa
     *                (ya validada a nivel de formato por el DTO)
     *                (será validada a nivel de negocio por el Use Case)
     * @return la persona guardada con ID asignado
     * @throws PersonaValidationException si falla la validación de dominio
     */
    Persona crear(Persona persona);

    /**
     * Caso de Uso 2: Buscar una Persona por ID
     *
     * @param id el identificador de la persona
     * @return la persona si existe
     * @throws PersonaNotFoundException si no existe
     */
    Persona buscarPorId(Long id);

    /**
     * Caso de Uso 3: Actualizar una Persona
     *
     * @param id el identificador de la persona a actualizar
     * @param datosNuevos la persona con los datos nuevos
     *                     (ya mapeada desde DTO)
     * @return la persona actualizada
     * @throws PersonaNotFoundException si no existe
     * @throws PersonaValidationException si los datos son inválidos
     */
    Persona actualizar(Long id, Persona datosNuevos);

    /**
     * Caso de Uso 4: Obtener todas las Personas
     *
     * @return lista de todas las personas
     */
    List<Persona> obtenerTodas();

    /**
     * Caso de Uso 5: Buscar Personas por Sexo
     *
     * Este método sigue recibiendo String porque es un PARÁMETRO DE FILTRO,
     * no un objeto completo. Es diferente a crear/actualizar.
     *
     * @param sexoString "M" o "F"
     * @return lista de personas con ese sexo
     */
    List<Persona> buscarPorSexo(String sexoString);

    /**
     * Caso de Uso 6: Eliminar una Persona por ID
     *
     * Este método recibe Long (ID) porque es un IDENTIFICADOR,
     * no un objeto completo.
     *
     * @param id el identificador de la persona a eliminar
     * @throws PersonaNotFoundException si no existe
     */
    void eliminar(Long id);
}
