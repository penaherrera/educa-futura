package sv.edu.udb.orientacionvocacional.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import sv.edu.udb.orientacionvocacional.repository.domain.Usuario;
import sv.edu.udb.orientacionvocacional.service.UsuarioService;
import sv.edu.udb.orientacionvocacional.service.UsuarioSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class UsuarioBean implements Serializable {

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private UsuarioSession usuarioSession;

    @Getter @Setter
    private String nombre;

    @Getter @Setter
    private String correo;

    @Getter @Setter
    private List<String> carrerasSeleccionadas = new ArrayList<>();

    public String ingresarUsuario() {
        Usuario usuarioExistente = usuarioService.obtenerUsuario(correo);

        if (usuarioExistente != null) {
            usuarioSession.setUsuarioId(usuarioExistente.getId());
            Integer preguntaActualId = usuarioExistente.getPreguntaActualId();

            if (preguntaActualId == null) {
                usuarioSession.setPreguntaActualId(Long.valueOf(1));
                return "pregunta1.xhtml?id=1&faces-redirect=true";
            }

            if (preguntaActualId.equals(10)) {
                return "resultado.xhtml?faces-redirect=true";
            }

            // Si no es null, se usa el valor de preguntaActualId
            usuarioSession.setPreguntaActualId(Long.valueOf(preguntaActualId));
            return "pregunta" + preguntaActualId + ".xhtml?id=" + preguntaActualId + "&faces-redirect=true";
        }

        String result = usuarioService.saveUsuario(nombre, correo);
        if ("success".equals(result)) {
            return "pregunta1.xhtml?id=1&faces-redirect=true";
        } else {
            return null;
        }
    }


    public String salir(){

        usuarioSession.setUsuarioId(null);
        usuarioSession.setPreguntaActualId(null);

        return "index.xhtml?faces-redirect=true";

    }
}