package sv.edu.udb.orientacionvocacional.repository.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;  // Relación con la entidad Usuario

    @ManyToOne
    @JoinColumn(name = "pregunta_id", nullable = false)
    private Pregunta pregunta; // Relación con la entidad Pregunta

    @Column(name = "numero_respuesta", nullable = false)
    private int numero_respuesta;  // Almacena la respuesta del usuario
}
