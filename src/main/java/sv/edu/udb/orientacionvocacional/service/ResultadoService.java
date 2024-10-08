package sv.edu.udb.orientacionvocacional.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.udb.orientacionvocacional.repository.PreguntaRepository;
import sv.edu.udb.orientacionvocacional.repository.RespuestaRepository;
import sv.edu.udb.orientacionvocacional.repository.ResultadoRepository;
import sv.edu.udb.orientacionvocacional.repository.domain.Respuesta;
import sv.edu.udb.orientacionvocacional.repository.domain.Resultado;
import sv.edu.udb.orientacionvocacional.repository.domain.Usuario;

import java.util.List;

@Named
@RequestScoped
public class ResultadoService {

    @Inject
    private PreguntaRepository preguntaRepository;

    @Inject
    private RespuestaService respuestaService;

    @Inject
    private ResultadoRepository resultadoRepository;

    @Inject
    private UsuarioService usuarioService;


    public void crearResultado(Long Id) {
        // Obtener todas las respuestas del usuario
        List<Respuesta> respuestas = respuestaService.getRespuestasByUser(Id);
        Usuario usuario = usuarioService.obtenerUsuarioActual();

        // Inicializar contadores para cada valor de numero_respuesta
        int contador1 = 0;
        int contador2 = 0;
        int contador3 = 0;
        int contador4 = 0;

        // Recorrer las respuestas y contar cuántas tienen cada valor de numero_respuesta
        for (Respuesta respuesta : respuestas) {
            int numeroRespuesta = respuesta.getNumero_respuesta();
            switch (numeroRespuesta) {
                case 1:
                    contador1++;
                    break;
                case 2:
                    contador2++;
                    break;
                case 3:
                    contador3++;
                    break;
                case 4:
                    contador4++;
                    break;
                default:
                    break;
            }
        }

        // Calcular el total de respuestas
        int totalRespuestas = respuestas.size();

        // Calcular los porcentajes
        double porcentaje1 = (totalRespuestas > 0) ? (contador1 * 100.0 / totalRespuestas) : 0;
        double porcentaje2 = (totalRespuestas > 0) ? (contador2 * 100.0 / totalRespuestas) : 0;
        double porcentaje3 = (totalRespuestas > 0) ? (contador3 * 100.0 / totalRespuestas) : 0;
        double porcentaje4 = (totalRespuestas > 0) ? (contador4 * 100.0 / totalRespuestas) : 0;

        // Crear un nuevo objeto Resultado con estos porcentajes
        Resultado resultado = new Resultado();
        resultado.setPorcentajeCarrera1(porcentaje1);
        resultado.setPorcentajeCarrera2(porcentaje2);
        resultado.setPorcentajeCarrera3(porcentaje3);
        resultado.setPorcentajeCarrera4(porcentaje4);
        resultado.setUsuario(usuario);


        resultadoRepository.save(resultado);
    }

    public Resultado obtenerResultadoPorUsuario() {
        Usuario usuario = usuarioService.obtenerUsuarioActual();

        if (usuario != null) {
            return resultadoRepository.findByUsuario(usuario.getId());
        }

        return null;
    }
}
