package sv.edu.udb.orientacionvocacional.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;
import sv.edu.udb.orientacionvocacional.repository.RespuestaRepository;
import sv.edu.udb.orientacionvocacional.repository.domain.Respuesta;
import sv.edu.udb.orientacionvocacional.repository.domain.Usuario; // Asegúrate de importar la clase Usuario

@Named // Asegúrate de anotar la clase con @Named para que se pueda inyectar
@RequestScoped
@NoArgsConstructor(force = true)
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;

    @Inject // Inyección de dependencia
    private UsuarioService usuarioService; // Inyectar UsuarioService

    @Inject
    public RespuestaService(RespuestaRepository respuestaRepository) {
        this.respuestaRepository = respuestaRepository;
    }

    public void saveRespuesta(Respuesta respuesta) {
        Usuario usuario = usuarioService.obtenerUsuarioActual(); // Obtener el usuario actual
        if (usuario != null) {
            respuesta.setUsuario(usuario); // Asignar el usuario a la respuesta
        }
        respuestaRepository.save(respuesta); // Guardar la respuesta
    }
}
