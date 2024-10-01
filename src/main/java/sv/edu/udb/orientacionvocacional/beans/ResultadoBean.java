package sv.edu.udb.orientacionvocacional.beans;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        cargarResultado();
    }

    public void cargarResultado() {
        this.resultado = resultadoService.obtenerResultadoPorUsuario();
        System.out.println("Este es el resultado: " + resultado);
    }
}
