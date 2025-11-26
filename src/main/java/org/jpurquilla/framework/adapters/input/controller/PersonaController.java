package org.jpurquilla.framework.adapters.input.controller;

import org.jpurquilla.application.port.input.PersonaInputPort;
import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.framework.adapters.input.dto.PersonaRequestDTO;
import org.jpurquilla.framework.adapters.input.dto.PersonaResponseDTO;
import org.jpurquilla.framework.adapters.input.mapper.PersonaInputMapper;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("/persona")
public class PersonaController {
    private final PersonaInputPort personaService;
    private final PersonaInputMapper personaInputMapper;


    public PersonaController(PersonaInputPort personaService, PersonaInputMapper personaInputMapper) {
        this.personaService = personaService;
        this.personaInputMapper = personaInputMapper;
    }

    /**
     * POST /api/personas
     * Crear una nueva persona
     *
     * FLUJO:
     * 1. Recibe PersonaRequestDTO
     * 2. Valida automáticamente (anotaciones @NotBlank, @Min, etc.)
     * 3. Si validación falla → ConstraintViolationException → GlobalExceptionHandler → 400
     * 4. Mapea DTO → Persona (dominio)
     * 5. Llama servicio.crear(persona)
     * 6. En el servicio:
     *    - CreatePersonaUseCase.ejecutar()
     *    - persona.validar() → si falla → PersonaValidationException → GlobalExceptionHandler → 400
     *    - repository.save() → persiste en BD
     * 7. Mapea Persona → PersonaResponseDTO
     * 8. Devuelve 201 Created
     *
     * @param dto los datos de la persona a crear
     * @return Response 201 con PersonaResponseDTO
     */
    @POST
    @Transactional
    public Response crear(PersonaRequestDTO dto) {
        // 1. Mapear DTO → Persona de dominio
        Persona persona = personaInputMapper.toPersonaDomain(dto);

        // 2. Llamar al servicio
        // Si hay excepción, GlobalExceptionHandler la captura
        Persona personaGuardada = personaService.crear(persona);

        // 3. Mapear Persona → DTO respuesta
        PersonaResponseDTO response = personaInputMapper.toDtoResponse(personaGuardada);

        // 4. Devolver 201 Created
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    /**
     * GET /api/personas/{id}
     * Buscar una persona por ID
     *
     * FLUJO:
     * 1. Recibe ID
     * 2. Llama servicio.buscarPorId(id)
     * 3. Si no existe → PersonaNotFoundException → GlobalExceptionHandler → 404
     * 4. Mapea respuesta
     * 5. Devuelve 200 OK
     *
     * @param id el identificador de la persona
     * @return Response 200 con PersonaResponseDTO
     */
    @GET
    @Path("/idpersona/{idpersona}")
    public PersonaResponseDTO buscarPorId(@PathParam("idpersona") Long id) {
        // 1. Llamar al servicio
        Persona persona = personaService.buscarPorId(id);

        // 2. Mapear respuesta
        return personaInputMapper.toDtoResponse(persona);

    }

    /**
     * PUT /api/personas/{id}
     * Actualizar una persona
     *
     * FLUJO:
     * 1. Recibe ID y PersonaRequestDTO
     * 2. Valida DTO automáticamente
     * 3. Mapea DTO → Persona
     * 4. Llama servicio.actualizar(id, persona)
     * 5. Si no existe → PersonaNotFoundException → 404
     * 6. Si validación falla → PersonaValidationException → 400
     * 7. Mapea respuesta
     * 8. Devuelve 200 OK
     *
     * @param id el identificador de la persona a actualizar
     * @param dto los nuevos datos
     * @return Response 200 con PersonaResponseDTO
     */
    @PUT
    @Path("/idpersona/{idpersona}")
    @Transactional
    public PersonaResponseDTO actualizar(@PathParam("idpersona") Long id,
                                 PersonaRequestDTO dto ) {
        // 1. Mapear DTO → Persona
        Persona datosNuevos = personaInputMapper.toPersonaDomain(dto);

        // 2. Llamar al servicio
        Persona personaActualizada = personaService.actualizar(id, datosNuevos);

        // 3. Mapear respuesta
        return personaInputMapper.toDtoResponse(personaActualizada);


    }

    /**
     * GET /api/personas
     * Obtener todas las personas
     *
     * FLUJO:
     * 1. Llama servicio.obtenerTodas()
     * 2. Mapea cada Persona → PersonaResponseDTO
     * 3. Devuelve lista en 200 OK
     *
     * @return Response 200 con List<PersonaResponseDTO>
     */
    @GET
    public List<PersonaResponseDTO> obtenerTodas() {
        return  personaService.obtenerTodas().stream()
                .map(personaInputMapper::toDtoResponse)
                .toList();


    }

    /**
     * GET /api/personas/por-sexo/{sexo}
     * Buscar personas por sexo
     *
     * FLUJO:
     * 1. Recibe sexo (M o F)
     * 2. Llama servicio.buscarPorSexo(sexo)
     * 3. Si sexo inválido → PersonaValidationException → 400
     * 4. Mapea respuesta
     * 5. Devuelve 200 OK
     *
     * @param sexo el sexo a filtrar (M o F)
     * @return Response 200 con List<PersonaResponseDTO>
     */
    @GET
    @Path("/sexo/{sexo}")

    public List<PersonaResponseDTO> buscarPorSexo(@PathParam("sexo") String sexo) {

        return personaService.buscarPorSexo(sexo).stream()
                .map(personaInputMapper::toDtoResponse)
                .toList();

    }

    /**
     * DELETE /api/personas/{id}
     * Eliminar una persona
     *
     * FLUJO:
     * 1. Recibe ID
     * 2. Llama servicio.eliminar(id)
     * 3. Si no existe → PersonaNotFoundException → 404
     * 4. Devuelve 204 No Content
     *
     * @param id el identificador de la persona a eliminar
     * @return Response 204 No Content
     */
    @DELETE
    @Path("/idpersona/{idpersona}")
    @Transactional
    public Response eliminar(@PathParam("idpersona") Long id) {
        // 1. Llamar al servicio
        personaService.eliminar(id);

        // 2. Devolver 204 (sin contenido)
        return Response.noContent().build();
    }
}
