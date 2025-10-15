package org.example.apilab2.service.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name="evaluations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Evaluation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private LocalDate date;
    @Column(nullable=false) private Double score;
    @Column(length=500) private String notes;
    @ManyToOne(optional=false, fetch=FetchType.LAZY) private Participant participant;
}
