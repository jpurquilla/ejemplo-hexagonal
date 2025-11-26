package org.jpurquilla.application.usecase;

import org.jpurquilla.application.port.output.PersonaRepository;
import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.domain.vo.Sexo;

import java.util.List;

public class BuscarPersonaPorSexoUseCase {
    private final PersonaRepository personaRepository;

    public BuscarPersonaPorSexoUseCase(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * Ejecuta el caso de uso: Buscar por Sexo
     *
     * @param sexoString "M" o "F"
     * @return lista de personas con ese sexo
     */
    public List<Persona> ejecutar(String sexoString) {
        Sexo sexo = Sexo.fromCodigo(sexoString);
        return personaRepository.findBySexo(sexo);
    }
}
