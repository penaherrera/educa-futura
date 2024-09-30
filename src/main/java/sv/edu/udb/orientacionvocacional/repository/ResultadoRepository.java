package sv.edu.udb.orientacionvocacional.repository;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sv.edu.udb.orientacionvocacional.repository.domain.Respuesta;
import sv.edu.udb.orientacionvocacional.repository.domain.Resultado;

import java.util.List;
import java.util.Optional;

@Named
public class ResultadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Resultado resultado) {
        entityManager.persist(resultado);
    }

    public Resultado findByUsuario(Long usuarioId) {
        try {
            // Consulta para buscar el resultado del usuario
            return entityManager.createQuery("SELECT r FROM Resultado r WHERE r.usuario.id = :usuarioId", Resultado.class)
                    .setParameter("usuarioId", usuarioId)
                    .getSingleResult();
        } catch (Exception e) {
            // Manejar cualquier excepción (por ejemplo, si no se encuentra el resultado)
            return null;
        }
    }

}
