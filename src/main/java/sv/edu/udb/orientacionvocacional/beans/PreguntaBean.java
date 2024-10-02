package sv.edu.udb.orientacionvocacional.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
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
import sv.edu.udb.orientacionvocacional.service.UsuarioSession;
import sv.edu.udb.orientacionvocacional.service.ResultadoService;

import java.io.Serializable;
import java.util.Optional;

import java.io.Serializable;

@Named
@ViewScoped
public class PreguntaBean implements Serializable {

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
    private UsuarioSession usuarioSession;

    @Inject
    private PreguntaService preguntaService;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private ResultadoService resultadoService;


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
        Optional<Respuesta> respuestaActual = respuestaService.getRespuestaByUserRespuesta();

        if (preguntaActual != null) {
            this.texto = preguntaActual.getTexto();
            this.respuesta = 1; // Inicializa respuesta a 1 para evitar respuestas vacías
        }

        respuestaActual.ifPresent(respuesta -> this.respuesta = respuesta.getNumero_respuesta());
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
            return "index.xhtml?faces-redirect=true";
        }

        nuevaRespuesta.setPregunta(preguntaActual);

        nuevaRespuesta.setNumero_respuesta(respuesta);

        respuestaService.saveRespuesta(nuevaRespuesta);

        Long siguientePreguntaId = preguntaActual.getId() + 1;
       //agrega un mensaje al confirmar respuesta
        addMessage("Confirmado", "Respuesta Enviada");
        return "pregunta" + siguientePreguntaId + ".xhtml?id=" + siguientePreguntaId + "&faces-redirect=true";

    }

    public String regresar(){

        Pregunta preguntaActual = preguntaService.obtenerPreguntaActual();
        Long preguntaAnteriorId = preguntaActual.getId() - 1;

        usuarioSession.setPreguntaActualId(Long.valueOf(preguntaAnteriorId));
        return "pregunta" + preguntaAnteriorId + ".xhtml?id=" + preguntaAnteriorId + "&faces-redirect=true";

    }

    //funcion para mostrar mensaje
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public String finalizar() {
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
            return "index.xhtml?faces-redirect=true";
        }

        nuevaRespuesta.setPregunta(preguntaActual);

        nuevaRespuesta.setNumero_respuesta(respuesta);

        respuestaService.saveRespuesta(nuevaRespuesta);

        resultadoService.crearResultado(usuarioActual.getId());
        addMessage("Confirmado", "Respuesta Enviada");

        return "resultado.xhtml?faces-redirect=true";

    }


}
