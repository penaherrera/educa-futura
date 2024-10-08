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

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private UsuarioSession usuarioSession;

    public String saveUsuario(String nombre, String correo) {
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

    public Usuario obtenerUsuario(String correo) {
        if (correo == null || correo.isEmpty()) {
            return null;
        }
        return usuarioRepository.findByEmail(correo);
    }

    public void actualizarPreguntaId(Long nuevaPreguntaId) {

        Long usuarioId = usuarioSession.getUsuarioId();


        if (usuarioId != null) {

            Usuario usuario = usuarioRepository.findById(usuarioId);


            if (usuario != null) {
                usuario.setPreguntaActualId(Math.toIntExact(nuevaPreguntaId));
                usuarioRepository.update(usuario);
            }
        }
    }

}
