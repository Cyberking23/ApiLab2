
package org.example.apilab2.service.entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Comunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String ubicacion;
    private String indiceVulnerabilidad;

    @ManyToMany(mappedBy = "comunidades")
    private List<Participante> participantes = new ArrayList<>();

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getIndiceVulnerabilidad() { return indiceVulnerabilidad; }
    public void setIndiceVulnerabilidad(String indiceVulnerabilidad) { this.indiceVulnerabilidad = indiceVulnerabilidad; }
    public List<Participante> getParticipantes() { return participantes; }
    public void setParticipantes(List<Participante> participantes) { this.participantes = participantes; }
}
