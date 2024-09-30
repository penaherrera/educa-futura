package sv.edu.udb.orientacionvocacional.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;
import sv.edu.udb.orientacionvocacional.repository.RespuestaRepository;
import sv.edu.udb.orientacionvocacional.repository.domain.Respuesta;
import sv.edu.udb.orientacionvocacional.repository.domain.Usuario;

import java.util.Optional;

@Named
@RequestScoped
@NoArgsConstructor(force = true)
public class RespuestaService {

    @Inject
    private RespuestaRepository respuestaRepository;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private PreguntaService preguntaService;

    @Inject
    private UsuarioSession usuarioSession;


    public void saveRespuesta(Respuesta respuesta) {

        Usuario usuario = usuarioService.obtenerUsuarioActual();
        Long preguntaId = preguntaService.obtenerPreguntaActual().getId();

        if (usuario != null && preguntaId != null) {
            respuesta.setUsuario(usuario);
            Optional<Respuesta> respuestaExistente = respuestaRepository.findByUsuarioAndPregunta(usuario.getId(), preguntaId);

            if (respuestaExistente.isPresent()) {
                respuestaRepository.update(respuestaExistente.get().getId(), respuesta);
            } else {
                respuestaRepository.save(respuesta);
            }
            Long preguntaActualId =  preguntaId + 1;
            usuarioSession.setPreguntaActualId(Long.valueOf(preguntaActualId));
        }
    }

    public Optional<Respuesta> getRespuestaByUserRespuesta() {

        Usuario usuario = usuarioService.obtenerUsuarioActual();
        Long preguntaId = preguntaService.obtenerPreguntaActual().getId();

        Optional<Respuesta> respuestaExistente = respuestaRepository.findByUsuarioAndPregunta(usuario.getId(), preguntaId);

        if (respuestaExistente.isPresent()) {
            return respuestaExistente;
        } else {
            return Optional.empty();
        }
    }

}
