package org.jpurquilla.framework.adapters.output.persistence;

import org.jpurquilla.domain.entity.Persona;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.List;

@ApplicationScoped
public class PersonaJpaRepository implements PanacheRepositoryBase<PersonaJpaEntity,Long>{
    public List<PersonaJpaEntity> buscarPersonasPorSexo (String sexo) {
        return list("sexo = ?1",sexo);
    }
}
