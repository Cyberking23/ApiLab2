
package org.example.apilab2.service.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private Double puntajeTecnologiaInicial;
    private Double puntajeTecnologiaFinal;
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participante_id", nullable = false)
    private Participante participante;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Double getPuntajeTecnologiaInicial() { return puntajeTecnologiaInicial; }
    public void setPuntajeTecnologiaInicial(Double puntajeTecnologiaInicial) { this.puntajeTecnologiaInicial = puntajeTecnologiaInicial; }
    public Double getPuntajeTecnologiaFinal() { return puntajeTecnologiaFinal; }
    public void setPuntajeTecnologiaFinal(Double puntajeTecnologiaFinal) { this.puntajeTecnologiaFinal = puntajeTecnologiaFinal; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Participante getParticipante() { return participante; }
    public void setParticipante(Participante participante) { this.participante = participante; }
}
