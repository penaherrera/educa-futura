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
    private UsuarioSession usuarioSession;

    @Inject
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String saveUsuario(String nombre, String correo) {
        System.out.println("Estoy en Save Usuario");
        if (nombre == null || nombre.isEmpty()) {
            return "El nombre es requerido";
        }

        if (correo == null || correo.isEmpty()) {
            return "El correo es requerido";
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCorreoElectronico(correo);
        usuarioRepository.save(usuario);

        usuarioSession.setUsuarioId(usuario.getId());

        usuarioSession.setPreguntaActualId(Long.valueOf(1));


        return "success";
    }

    public Usuario obtenerUsuarioActual() {
        Long usuarioId = usuarioSession.getUsuarioId();
        if (usuarioId != null) {
            return usuarioRepository.findById(usuarioId);
        }
        return null;
    }
}
