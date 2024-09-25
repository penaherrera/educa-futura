package sv.edu.udb.orientacionvocacional.repository;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sv.edu.udb.orientacionvocacional.repository.domain.Usuario;

@Named
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Usuario usuario) {
        entityManager.persist(usuario);
    }

    @Transactional
    public Usuario findById(Long id) {
        if (id == null) {
            return null; // Manejar el caso nulo
        }
        return entityManager.find(Usuario.class, id); // Buscar el usuario
    }
}
