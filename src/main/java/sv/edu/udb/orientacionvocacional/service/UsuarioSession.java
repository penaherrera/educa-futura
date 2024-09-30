package sv.edu.udb.orientacionvocacional.service;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Named
@SessionScoped
public class UsuarioSession implements Serializable {
    @Setter
    @Getter
    private Long usuarioId;

    @Getter @Setter
    private Long preguntaActualId;

}
