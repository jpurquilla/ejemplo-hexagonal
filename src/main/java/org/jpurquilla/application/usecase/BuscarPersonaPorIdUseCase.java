package org.jpurquilla.application.usecase;

import org.jpurquilla.application.port.output.PersonaRepository;
import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.domain.exceptions.PersonaNotFoundException;

public class BuscarPersonaPorIdUseCase {
    private final PersonaRepository personaRepository;

    /**
     * Constructor: inyección manual de dependencias
     *
     * @param repository el puerto de persistencia
     */
    public BuscarPersonaPorIdUseCase(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * Ejecuta el caso de uso: Buscar por ID
     *
     * @param id el identificador de la persona
     * @return la persona si existe
     * @throws PersonaNotFoundException si no existe
     */
    public Persona ejecutar(Long id) {
        return personaRepository.findById(id)
                .orElseThrow(() -> new PersonaNotFoundException(id));
    }
}
