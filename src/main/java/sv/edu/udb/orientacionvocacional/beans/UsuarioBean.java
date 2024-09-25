package sv.edu.udb.orientacionvocacional.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import lombok.Getter;
import lombok.Setter;
import sv.edu.udb.orientacionvocacional.service.UsuarioService;
import java.io.Serializable;

@Named
@RequestScoped
public class UsuarioBean implements Serializable {

    @Inject
    private UsuarioService usuarioService;

    @Getter @Setter
    private String nombre;

    public String saveUsuario() {
        String result = usuarioService.saveUsuario(nombre);
        if ("success".equals(result)) {
            System.out.println("Si se creo el usuario");
            return "pregunta1.xhtml?id=1&faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, result, null));
            return null;
        }
    }
}