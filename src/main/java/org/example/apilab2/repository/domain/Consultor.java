package org.example.apilab2.repository.domain;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity @Table(name = "consultores")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Consultor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String nombre;
    @Column(nullable=false) private String especializacion;
    private Boolean inActivoActualizado;

    @OneToMany(mappedBy = "consultor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Programa> programas;
}
