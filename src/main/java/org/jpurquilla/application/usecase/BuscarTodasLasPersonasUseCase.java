package org.jpurquilla.application.usecase;

import org.jpurquilla.application.port.output.PersonaRepository;
import org.jpurquilla.domain.entity.Persona;

import java.util.List;

public class BuscarTodasLasPersonasUseCase {
    private final PersonaRepository personaRepository;


    public BuscarTodasLasPersonasUseCase(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * Ejecuta el caso de uso: Obtener todas
     *
     * @return lista de todas las personas
     */
    public List<Persona> ejecutar() {
        return personaRepository.findAll();
    }
}
