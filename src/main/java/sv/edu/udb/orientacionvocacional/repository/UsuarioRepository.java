package sv.edu.udb.orientacionvocacional.repository;

import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
    public void update(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Transactional
    public Usuario findById(Long id) {
        if (id == null) {
            return null; // Manejar el caso nulo
        }
        return entityManager.find(Usuario.class, id); // Buscar el usuario
    }

    @Transactional
    public Usuario findByEmail(String email) {
        if ((email == null) || (email.trim().length() == 0)) {
            return null;
        }
        // Utilizar una consulta JPQL para buscar por el campo correo electrónico
        try {
            return entityManager.createQuery("SELECT u FROM Usuario u WHERE u.correoElectronico = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retornar null si no se encuentra ningún resultado
        }
    }

}
