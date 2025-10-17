
package org.example.apilab2.service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Entity
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    private Integer edad;
    private String genero;
    private String nivelEducativo;
    private Double ingresoFamiliar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programa_id")
    private Programa programa;

    @ManyToMany
    @JoinTable(name = "participante_comunidad",
            joinColumns = @JoinColumn(name = "participante_id"),
            inverseJoinColumns = @JoinColumn(name = "comunidad_id"))
    private List<Comunidad> comunidades = new ArrayList<>();

    @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones = new ArrayList<>();

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getNivelEducativo() { return nivelEducativo; }
    public void setNivelEducativo(String nivelEducativo) { this.nivelEducativo = nivelEducativo; }
    public Double getIngresoFamiliar() { return ingresoFamiliar; }
    public void setIngresoFamiliar(Double ingresoFamiliar) { this.ingresoFamiliar = ingresoFamiliar; }
    public Programa getPrograma() { return programa; }
    public void setPrograma(Programa programa) { this.programa = programa; }
    public List<Comunidad> getComunidades() { return comunidades; }
    public void setComunidades(List<Comunidad> comunidades) { this.comunidades = comunidades; }
    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(List<Evaluacion> evaluaciones) { this.evaluaciones = evaluaciones; }
}
