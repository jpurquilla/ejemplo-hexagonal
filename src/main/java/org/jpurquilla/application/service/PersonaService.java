package org.jpurquilla.application.service;

import org.jpurquilla.application.port.input.PersonaInputPort;
import org.jpurquilla.application.port.output.PersonaRepository;
import org.jpurquilla.application.usecase.*;
import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.framework.adapters.input.dto.PersonaRequestDTO;
import org.jpurquilla.framework.adapters.input.dto.PersonaResponseDTO;

import java.util.List;


/**
 * SERVICIO DE APLICACIÓN: PersonaService
 *
 * ¿QUÉ ES?
 * - Orquesta los 6 Use Cases
 * - Implementa PersonaInputPort (define el contrato de entrada)
 * - Es la capa de servicio de la aplicación
 *
 * ¿POR QUÉ NO TIENE ANOTACIONES?
 * - Está en application (lógica pura)
 * - No debe depender de Quarkus (@ApplicationScoped, @Inject)
 * - Es instanciada por ApplicationConfig en framework
 *
 * ¿RESPONSABILIDADES?
 * - Orquestar Use Cases
 * - Delegar la lógica a los Use Cases
 * - NO tiene lógica de negocio (eso es de los Use Cases)
 * - NO tiene transacciones (eso es responsabilidad del Controller)
 *
 * NOTA: Las transacciones van en el Controller (framework)
 * porque es donde ocurre la entrada/salida y donde se necesita
 * garantizar la consistencia de la BD.
 */
public class PersonaService implements PersonaInputPort {
    // inyección manual: todos los Use Cases se reciben en el constructor
    private final CrearPersonaUseCase crearPersonaUseCase;
    private final BuscarPersonaPorIdUseCase buscarPersonaPorIdUseCase;
    private final ActualizarPersonaUseCase actualizarPersonaUseCase;
    private final BuscarTodasLasPersonasUseCase buscarTodasLasPersonasUseCase;
    private final BuscarPersonaPorSexoUseCase buscarPersonaPorSexoUseCase;
    private final EliminarPersonaUseCase eliminarPersonaUseCase;

    public PersonaService(PersonaRepository personaRepository) {
        this.crearPersonaUseCase = new CrearPersonaUseCase(personaRepository);
        this.buscarPersonaPorIdUseCase = new BuscarPersonaPorIdUseCase(personaRepository);
        this.actualizarPersonaUseCase = new ActualizarPersonaUseCase(personaRepository);
        this.buscarTodasLasPersonasUseCase = new BuscarTodasLasPersonasUseCase(personaRepository);
        this.buscarPersonaPorSexoUseCase = new BuscarPersonaPorSexoUseCase(personaRepository);
        this.eliminarPersonaUseCase = new EliminarPersonaUseCase(personaRepository);
    }

    @Override
    public Persona crear(Persona persona) {
        return crearPersonaUseCase.ejecutar(persona);
    }

    @Override
    public Persona buscarPorId(Long id) {
        return buscarPersonaPorIdUseCase.ejecutar(id);
    }

    @Override
    public Persona actualizar(Long id, Persona personaDatosNuevo) {
        return actualizarPersonaUseCase.ejecutar(id,personaDatosNuevo);
    }

    @Override
    public List<Persona> obtenerTodas() {
        return buscarTodasLasPersonasUseCase.ejecutar();
    }

    @Override
    public List<Persona> buscarPorSexo(String sexo) {
        return buscarPersonaPorSexoUseCase.ejecutar(sexo);
    }

    @Override
    public void eliminar(Long id) {
        eliminarPersonaUseCase.ejecutar(id);
    }
}
