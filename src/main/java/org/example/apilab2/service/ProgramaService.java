
package org.example.apilab2.service;

import org.example.apilab2.service.dtos.ProgramaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProgramaService {
    ProgramaDto crear(ProgramaDto dto);
    ProgramaDto obtener(Long id);
    Page<ProgramaDto> listar(Pageable pageable);
    ProgramaDto actualizar(Long id, ProgramaDto dto);
    void eliminar(Long id);
}
