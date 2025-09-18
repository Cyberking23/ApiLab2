package org.example.apilab2.service;
import org.example.apilab2.repository.domain.Consultor;
public interface ConsultorService {
    Consultor crear(String nombre, String especializacion, Boolean inActivoActualizado);
}
