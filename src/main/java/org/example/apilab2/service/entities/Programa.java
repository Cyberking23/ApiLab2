
package org.example.apilab2.service.entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Programa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String enfoquePedagogico;
    private String duracion;
    private String institucion;

    @OneToMany(mappedBy = "programa", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Participante> participantes = new ArrayList<>();

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEnfoquePedagogico() { return enfoquePedagogico; }
    public void setEnfoquePedagogico(String enfoquePedagogico) { this.enfoquePedagogico = enfoquePedagogico; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    public String getInstitucion() { return institucion; }
    public void setInstitucion(String institucion) { this.institucion = institucion; }
    public List<Participante> getParticipantes() { return participantes; }
    public void setParticipantes(List<Participante> participantes) { this.participantes = participantes; }
}
