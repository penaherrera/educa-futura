package sv.edu.udb.orientacionvocacional.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import sv.edu.udb.orientacionvocacional.service.UsuarioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class UsuarioBean implements Serializable {

    @Inject
    private UsuarioService usuarioService;

    @Getter @Setter
    private String nombre;

    @Getter @Setter
    private String correo;

    @Getter @Setter
    private List<String> carrerasSeleccionadas = new ArrayList<>();

    public List<String> getCarrerasDisponibles() {
        // Devuelve la lista de carreras disponibles
        //mandar a llamar de la bd
        return List.of("Ingeniería en Sistemas", "Arquitectura", "Medicina", "Psicología");
    }
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