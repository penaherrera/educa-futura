package sv.edu.udb.orientacionvocacional.repository;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sv.edu.udb.orientacionvocacional.repository.domain.Respuesta;

import java.util.Optional;

@Named
public class RespuestaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Respuesta respuesta) {
        entityManager.persist(respuesta);
    }

    @Transactional
    public void update(Long id, Respuesta updatedRespuesta) {

        Respuesta respuesta = findById(id);

        if (respuesta != null) {
            respuesta.setNumero_respuesta(updatedRespuesta.getNumero_respuesta());
            entityManager.merge(respuesta);
        } else {
            throw new EntityNotFoundException("No se encontró la respuesta con ID: " + id);
        }
    }

    public Respuesta findById(final Long id) {
        return entityManager.find(Respuesta.class, id);
    }

    public Optional<Respuesta> findByUsuarioAndPregunta(Long usuarioId, Long preguntaId) {
        try {
            return Optional.of(
                    entityManager.createQuery("SELECT r FROM Respuesta r WHERE r.usuario.id = :usuarioId AND r.pregunta.id = :preguntaId", Respuesta.class)
                            .setParameter("usuarioId", usuarioId)
                            .setParameter("preguntaId", preguntaId)
                            .getSingleResult()
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
