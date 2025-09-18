package org.example.apilab2.repository.domain;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "programas")
public class Programa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String nombre;
    @Column(nullable=false) private String enfoque;   // basico/avanzado/...
    @Column(nullable=false) private String duracion;  // "40h"
    private String institucion;

    @ManyToOne(optional=false) @JoinColumn(name="consultor_id")
    private Consultor consultor;

    @ManyToMany
    @JoinTable(name="inscripciones",
            joinColumns = @JoinColumn(name="programa_id"),
            inverseJoinColumns = @JoinColumn(name="participante_id"))
    @Builder.Default
    private Set<Participante> participantes = new HashSet<>();
}
