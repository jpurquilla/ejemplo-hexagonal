package org.jpurquilla.framework.adapters.config;

import org.jpurquilla.application.port.input.PersonaInputPort;
import org.jpurquilla.application.port.output.PersonaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.jpurquilla.application.service.PersonaService;

@ApplicationScoped
public class ApplicationConfig {
    private final PersonaRepository personaRepository;

    public ApplicationConfig(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    /**
     * ÚNICO @Produces: PersonaInputPort
     *
     * Produce PersonaService que implementa PersonaInputPort.
     * PersonaService instancia los 6 Use Cases internamente.
     *
     * FLUJO:
     * 1. PersonaController necesita PersonaInputPort
     * 2. Quarkus busca productor
     * 3. Encuentra este método
     * 4. Ejecuta: new PersonaService(personaRepository)
     * 5. PersonaService.__init__() crea los 6 Use Cases
     * 6. Inyecta PersonaService en PersonaController
     *
     * @return una instancia de PersonaService (como PersonaInputPort)
     */
    @Produces
    @ApplicationScoped
    public PersonaInputPort personaService() {
        return new PersonaService(personaRepository);
    }
}
