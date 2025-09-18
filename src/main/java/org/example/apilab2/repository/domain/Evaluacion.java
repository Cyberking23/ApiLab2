package org.example.apilab2.repository.domain;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="evaluaciones",
        uniqueConstraints = @UniqueConstraint(name="uk_eval_prog_part_fecha",
                columnNames={"programa_id","participante_id","fecha"}))
public class Evaluacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private LocalDate fecha;
    @Column(nullable=false) private Double puntaje; // 0..100
    private String observaciones;

    @ManyToOne(optional=false) @JoinColumn(name="participante_id")
    private Participante participante;

    @ManyToOne(optional=false) @JoinColumn(name="programa_id")
    private Programa programa;
}
