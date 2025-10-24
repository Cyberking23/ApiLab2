
package org.example.apilab2.service;

import org.example.apilab2.service.dtos.ParticipanteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipanteService {
    ParticipanteDto crear(ParticipanteDto dto);
    ParticipanteDto obtener(Long id);
    Page<ParticipanteDto> listar(Long programaId, Long comunidadId, Pageable pageable);
    ParticipanteDto actualizar(Long id, ParticipanteDto dto);
    void eliminar(Long id);
}
