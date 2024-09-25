package sv.edu.udb.orientacionvocacional.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;
import sv.edu.udb.orientacionvocacional.repository.UsuarioRepository;
import sv.edu.udb.orientacionvocacional.repository.domain.Usuario;

@Named
@RequestScoped
@NoArgsConstructor(force = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Inject
    private UsuarioSession usuarioSession; // Inyecta la sesión del usuario

    @Inject
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String saveUsuario(String nombre) {
        System.out.println("Estoy en Save Usuario");
        if (nombre == null || nombre.isEmpty()) {
            return "El nombre es requerido";
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuarioRepository.save(usuario);

        usuarioSession.setUsuarioId(usuario.getId());

        usuarioSession.setPreguntaActualId(Long.valueOf(1));


        return "success";
    }

    // Cambia el método para retornar un objeto Usuario en lugar de Long
    public Usuario obtenerUsuarioActual() {
        Long usuarioId = usuarioSession.getUsuarioId(); // Método para obtener el ID del usuario actual
        if (usuarioId != null) {
            return usuarioRepository.findById(usuarioId); // Retorna el usuario completo
        }
        return null; // Retorna null si no hay un ID de usuario actual
    }
}
