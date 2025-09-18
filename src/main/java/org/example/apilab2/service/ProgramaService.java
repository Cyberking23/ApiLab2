package org.example.apilab2.service;
import org.example.apilab2.repository.domain.Programa;
public interface ProgramaService {
    Programa crear(String nombre, String enfoque, String duracion, String institucion, Long consultorId);
    Programa inscribirParticipante(Long programaId, Long participanteId);
}
