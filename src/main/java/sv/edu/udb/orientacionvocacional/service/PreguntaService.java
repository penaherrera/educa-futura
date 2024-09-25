package sv.edu.udb.orientacionvocacional.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;
import sv.edu.udb.orientacionvocacional.repository.PreguntaRepository;
import sv.edu.udb.orientacionvocacional.repository.domain.Pregunta;
import sv.edu.udb.orientacionvocacional.repository.domain.Respuesta;
import sv.edu.udb.orientacionvocacional.repository.domain.Usuario;

@Named // Asegúrate de anotar la clase con @Named para que se pueda inyectar
@RequestScoped
@NoArgsConstructor(force = true)
public class PreguntaService {

    private final PreguntaRepository preguntaRepository;

    @Inject
    public PreguntaService(PreguntaRepository preguntaRepository) {
        this.preguntaRepository = preguntaRepository;
    }

    @Inject
    private UsuarioSession usuarioSession; // Inyecta la sesión del usuario

    public Pregunta getPreguntaById(Long id) {
        return preguntaRepository.findById(id);
    }

    public Pregunta obtenerPreguntaActual() {
        Long preguntaId = usuarioSession.getPreguntaActualId();
        if (preguntaId != null) {
            return preguntaRepository.findById(preguntaId);
        }
        return null;
    }

}
