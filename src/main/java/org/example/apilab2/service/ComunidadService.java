
package org.example.apilab2.service;

import org.example.apilab2.service.dtos.ComunidadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComunidadService {
    ComunidadDto crear(ComunidadDto dto);
    ComunidadDto obtener(Long id);
    Page<ComunidadDto> listar(Pageable pageable);
    ComunidadDto actualizar(Long id, ComunidadDto dto);
    void eliminar(Long id);
}
