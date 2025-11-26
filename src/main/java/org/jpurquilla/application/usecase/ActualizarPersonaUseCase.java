package org.jpurquilla.application.usecase;

import org.jpurquilla.application.port.output.PersonaRepository;
import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.domain.exceptions.PersonaNotFoundException;
import org.jpurquilla.domain.vo.Sexo;

public class ActualizarPersonaUseCase {
    private final PersonaRepository personaRepository;

    /**
     * Constructor: inyección manual de dependencias
     *
     * @param repository el puerto de persistencia
     */
    public ActualizarPersonaUseCase(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * Ejecuta el caso de uso: Actualizar Persona
     *
     * @param id el identificador de la persona a actualizar
     * @param datosNuevos la persona con los datos nuevos (mapeada desde DTO)
     * @return la persona actualizada
     */
    public Persona ejecutar(Long id, Persona datosNuevos) {
        // Buscar la persona existente
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new PersonaNotFoundException(id));

        // Actualizar con los nuevos datos
        persona.actualizar(
                datosNuevos.getNombre(),
                datosNuevos.getApellido(),
                datosNuevos.getEdad(),
                datosNuevos.getSexo()
        );

        // Validar
        persona.validar();

        // Guardar
        return personaRepository.update(persona);
    }
}
