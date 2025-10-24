
package org.example.apilab2.service.impl;

import org.example.apilab2.repository.ComunidadRepository;
import org.example.apilab2.service.ComunidadService;
import org.example.apilab2.service.dtos.ComunidadDto;
import org.example.apilab2.service.entities.Comunidad;
import org.example.apilab2.service.exceptions.NotFoundException;
import org.example.apilab2.service.mappers.ComunidadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ComunidadServiceImpl implements ComunidadService {

    private final ComunidadRepository repo;
    private final ComunidadMapper mapper;

    public ComunidadServiceImpl(ComunidadRepository repo, ComunidadMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ComunidadDto crear(ComunidadDto dto) {
        Comunidad c = mapper.toEntity(dto);
        return mapper.toDto(repo.save(c));
    }

    @Override
    @Transactional(readOnly = true)
    public ComunidadDto obtener(Long id) {
        Comunidad c = repo.findById(id).orElseThrow(() -> new NotFoundException("Comunidad no encontrada"));
        return mapper.toDto(c);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComunidadDto> listar(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public ComunidadDto actualizar(Long id, ComunidadDto dto) {
        Comunidad c = repo.findById(id).orElseThrow(() -> new NotFoundException("Comunidad no encontrada"));
        c.setNombre(dto.nombre);
        c.setUbicacion(dto.ubicacion);
        c.setIndiceVulnerabilidad(dto.indiceVulnerabilidad);
        return mapper.toDto(repo.save(c));
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
