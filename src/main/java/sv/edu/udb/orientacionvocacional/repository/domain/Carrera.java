package sv.edu.udb.orientacionvocacional.repository.domain;
import jakarta.persistence.*;


@Entity
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

}
