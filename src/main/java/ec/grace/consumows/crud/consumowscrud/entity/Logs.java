package ec.grace.consumows.crud.consumowscrud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Getter
@Setter
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String cedula;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String accion;

    @Column(name = "fecha", updatable = false, insertable = false)
    private LocalDateTime fecha;

    @Column(length = 50, nullable = false)
    private String estado;

}
