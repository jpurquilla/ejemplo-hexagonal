package org.jpurquilla.application.usecase;

import org.jpurquilla.application.port.output.PersonaRepository;
import org.jpurquilla.domain.exceptions.PersonaNotFoundException;

public class EliminarPersonaUseCase {
    private final PersonaRepository personaRepository;


    public EliminarPersonaUseCase(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * Ejecuta el caso de uso: Eliminar
     *
     * @param id el identificador de la persona a eliminar
     * @throws PersonaNotFoundException si no existe
     */
    public void ejecutar(Long id) {
        boolean existe = personaRepository.findById(id).isPresent();

        if (!existe) {
            throw new PersonaNotFoundException(id);
        }

        personaRepository.deleteById(id);
    }
}
