package org.example.apilab2.service.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="trainers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Trainer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name;
    @Column(nullable=false) private Integer experienceYears;
    @Column(nullable=false) private String academicLevel;
    @OneToMany(mappedBy="trainer", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Participant> participants = new ArrayList<>();
}
