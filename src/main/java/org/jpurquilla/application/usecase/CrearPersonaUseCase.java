package org.jpurquilla.application.usecase;

import org.jpurquilla.application.port.output.PersonaRepository;
import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.domain.vo.Sexo;

/*
* Este es un servicio de negocio PURO.
* No tiene dependencias de framework.
*/
public class CrearPersonaUseCase {
    private final PersonaRepository personaRepository;

    /**
     * Constructor: inyección manual de dependencias
     * El Controller es responsable de inyectar el repositorio
     *
     * @param repository el puerto de persistencia
     */
    public CrearPersonaUseCase(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * Ejecuta el caso de uso: Crear Persona
     *
     * @param persona la persona de dominio (ya creada y mapeada desde DTO)
     * @return la persona guardada con ID
     */
    public Persona ejecutar(Persona persona) {
        // Validar
        persona.validar();

        // Guardar
        return personaRepository.save(persona);
    }

}
