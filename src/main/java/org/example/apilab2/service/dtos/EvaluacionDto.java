
package org.example.apilab2.service.dtos;

import java.time.LocalDate;

public class EvaluacionDto {
    public Long id;
    public LocalDate fecha;
    public Double puntajeTecnologiaInicial;
    public Double puntajeTecnologiaFinal;
    public String observaciones;
    public Long participanteId;
}
