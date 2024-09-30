package sv.edu.udb.orientacionvocacional.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sv.edu.udb.orientacionvocacional.repository.domain.Resultado;
import sv.edu.udb.orientacionvocacional.service.ResultadoService;

@Named
@RequestScoped
@NoArgsConstructor
public class ResultadoBean {

    @Inject
    private ResultadoService resultadoService;

    @Getter
    private Resultado resultado;

    public void cargarResultado() {
        // Llama al método de servicio para obtener el resultado del usuario actual
        this.resultado = resultadoService.obtenerResultadoPorUsuario();
    }
}
