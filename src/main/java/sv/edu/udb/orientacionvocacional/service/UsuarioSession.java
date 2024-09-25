package sv.edu.udb.orientacionvocacional.service;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;

@Named
@SessionScoped
public class UsuarioSession implements Serializable {
    @Getter
    private Long usuarioId;

    private Long preguntaActualId;

    public Long getPreguntaActualId() {
        return preguntaActualId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setPreguntaActualId(Long preguntaActualId) {
        this.preguntaActualId = preguntaActualId;
    }

}
