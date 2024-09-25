package sv.edu.udb.orientacionvocacional.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import sv.edu.udb.orientacionvocacional.repository.domain.Pregunta;
import sv.edu.udb.orientacionvocacional.repository.domain.Respuesta;
import sv.edu.udb.orientacionvocacional.repository.domain.Usuario;
import sv.edu.udb.orientacionvocacional.service.RespuestaService;
import sv.edu.udb.orientacionvocacional.service.PreguntaService;
import sv.edu.udb.orientacionvocacional.service.UsuarioService;

@Named
@RequestScoped
public class PreguntaBean {

    @Setter
    @Getter
    private String texto;

    @Setter
    @Getter
    private int respuesta;

    @Getter
    private Long preguntaId;

    @Inject
    private RespuestaService respuestaService;

    @Inject
    private PreguntaService preguntaService;

    @Inject
    private UsuarioService usuarioService;

    @PostConstruct
    public void init() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String idParam = externalContext.getRequestParameterMap().get("id");

        if (idParam != null) {
            this.preguntaId = Long.valueOf(idParam);
            cargarPregunta();
        } else {
            System.out.println("Dentro de post construct idParam es null");
        }
    }

    private void cargarPregunta() {
        Pregunta preguntaActual = preguntaService.getPreguntaById(preguntaId);
        if (preguntaActual != null) {
            this.texto = preguntaActual.getTexto();
        }
    }

    public String continuar() {
        Pregunta preguntaActual = preguntaService.obtenerPreguntaActual();

        if (preguntaActual == null) {
            System.out.println("preguntaActual es null");
            return "error.xhtml?faces-redirect=true";
        }

        Respuesta nuevaRespuesta = new Respuesta();

        Usuario usuarioActual = usuarioService.obtenerUsuarioActual();
        if (usuarioActual != null) {
            nuevaRespuesta.setUsuario(usuarioActual);
        } else {
            System.out.println("Usuario no encontrado");
            return "error.xhtml?faces-redirect=true";
        }

        nuevaRespuesta.setPregunta(preguntaActual);

        nuevaRespuesta.setNumero_respuesta(respuesta);

        respuestaService.saveRespuesta(nuevaRespuesta);

        Long siguientePreguntaId = preguntaActual.getId() + 1;

        return "pregunta" + siguientePreguntaId + ".xhtml?id=" + siguientePreguntaId + "&faces-redirect=true";
    }

}
