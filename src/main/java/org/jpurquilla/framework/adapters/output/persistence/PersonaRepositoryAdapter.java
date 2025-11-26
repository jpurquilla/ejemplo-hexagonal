package org.jpurquilla.framework.adapters.output.persistence;

import org.jpurquilla.application.port.output.PersonaRepository;
import org.jpurquilla.domain.entity.Persona;
import org.jpurquilla.domain.vo.Sexo;
import org.jpurquilla.framework.adapters.output.mapper.PersonaOutputMapper;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PersonaRepositoryAdapter implements PersonaRepository {
    private final PersonaJpaRepository personaJpaRepository;
    private final PersonaOutputMapper personaOutputMapper;

    public PersonaRepositoryAdapter(PersonaJpaRepository personaJpaRepository, PersonaOutputMapper personaOutputMapper) {
        this.personaJpaRepository = personaJpaRepository;
        this.personaOutputMapper = personaOutputMapper;
    }


    @Override
    public Persona save(Persona persona) {
        PersonaJpaEntity personaJpaEntity = personaOutputMapper.toJpaEntity(persona);
        personaJpaRepository.persist(personaJpaEntity);
        return personaOutputMapper.toDomain(personaJpaEntity);
    }

    @Override
    public Optional<Persona> findById(Long id) {
        return this.personaJpaRepository.findByIdOptional(id)
                // Si existe, convierte a Persona (dominio)
                .map(personaOutputMapper::toDomain);
    }

    @Override
    public Persona update(Persona persona) {
        // Paso 1: Convertir
         PersonaJpaEntity jpaEntity = personaOutputMapper.toJpaEntity(persona);

        // Paso 2: Persistir (detecta que tiene ID y hace UPDATE)
        this.personaJpaRepository.persist(jpaEntity);

        // Paso 3: Convertir de vuelta
        return personaOutputMapper.toDomain(jpaEntity);
    }

    @Override
    public List<Persona> findAll() {
        return this.personaJpaRepository.listAll()
                // Convertir cada una a Persona (dominio)
                .stream()
                .map(personaOutputMapper::toDomain)
                .toList();
    }

    @Override
    public List<Persona> findBySexo(Sexo sexo) {
        String codigo = sexo.getCodigo();
        return personaJpaRepository.buscarPersonasPorSexo(codigo)
                .stream().map(personaOutputMapper::toDomain)
                .toList();
    }

    @Override
    public boolean deleteById(Long id) {
        return personaJpaRepository.findByIdOptional(id)
                .map(entity -> {
                    // Si existe, eliminar
                    personaJpaRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public long count() {
        return personaJpaRepository.count();
    }
}
