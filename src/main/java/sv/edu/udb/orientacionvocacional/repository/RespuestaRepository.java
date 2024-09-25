package sv.edu.udb.orientacionvocacional.repository;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sv.edu.udb.orientacionvocacional.repository.domain.Respuesta;

@Named
public class RespuestaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Respuesta respuesta) {
        entityManager.persist(respuesta);
    }

    public void update(Respuesta respuesta) {
        entityManager.merge(respuesta); // Actualiza la entidad
    }
}
