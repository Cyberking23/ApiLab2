package org.example.apilab2.service.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="participants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Participant {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name;
    @Column(nullable=false) private Integer age;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Gender gender;
    @Column(nullable=false) private String educationLevel;
    @Column(nullable=false) private BigDecimal familyIncome;
    @Column(nullable=false, unique=true) private String email;
    @ManyToOne(optional=false, fetch=FetchType.LAZY) private Trainer trainer;
    @OneToMany(mappedBy="participant", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Evaluation> evaluations = new ArrayList<>();
}
