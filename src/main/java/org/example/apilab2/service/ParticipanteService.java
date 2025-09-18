package org.example.apilab2.service;
import org.example.apilab2.repository.domain.Participante;
public interface ParticipanteService {
    Participante crear(String nombre, String email, String genero, String nivelEducativo, Boolean ingresoFormal);
}
