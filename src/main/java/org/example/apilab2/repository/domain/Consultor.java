package org.example.apilab2.repository.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "consultores")
public class Consultor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String nombre;
    @Column(nullable=false) private String especializacion;
    private Boolean inActivoActualizado;

    @OneToMany(mappedBy = "consultor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Programa> programas;
}
