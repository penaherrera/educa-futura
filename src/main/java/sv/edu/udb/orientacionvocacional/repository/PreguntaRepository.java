package sv.edu.udb.orientacionvocacional.repository;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.udb.orientacionvocacional.repository.domain.Pregunta;

@Named
public class PreguntaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Pregunta findById(final Long id) {
        return entityManager.find(Pregunta.class, id);
    }

}
