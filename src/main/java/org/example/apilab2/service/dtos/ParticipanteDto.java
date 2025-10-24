
package org.example.apilab2.service.dtos;

import java.util.List;

public class ParticipanteDto {
    public Long id;
    public String nombre;
    public Integer edad;
    public String genero;
    public String nivelEducativo;
    public Double ingresoFamiliar;
    public Long programaId;
    public List<Long> comunidadesIds;
}
