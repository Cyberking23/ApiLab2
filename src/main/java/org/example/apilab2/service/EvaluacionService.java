
package org.example.apilab2.service;

import org.example.apilab2.service.dtos.EvaluacionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EvaluacionService {
    EvaluacionDto crear(EvaluacionDto dto);
    EvaluacionDto obtener(Long id);
    Page<EvaluacionDto> listar(Long participanteId, Pageable pageable);
    EvaluacionDto actualizar(Long id, EvaluacionDto dto);
    void eliminar(Long id);
}
