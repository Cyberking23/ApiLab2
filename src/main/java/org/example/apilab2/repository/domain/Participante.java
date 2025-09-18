package org.example.apilab2.repository.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "participantes")
public class Participante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false) private String nombre;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String genero;
    private String nivelEducativo;
    private Boolean ingresoFormal;

    @ManyToMany(mappedBy = "participantes")
    private Set<Programa> programas;
}
