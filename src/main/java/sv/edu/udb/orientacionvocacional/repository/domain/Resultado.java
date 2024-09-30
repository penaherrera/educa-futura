package sv.edu.udb.orientacionvocacional.repository.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;  // Relación con la entidad Usuario

    @Column
    private double porcentajeCarrera1;

    @Column
    private double porcentajeCarrera2;

    @Column
    private double porcentajeCarrera3;

    @Column
    private double porcentajeCarrera4;

}

