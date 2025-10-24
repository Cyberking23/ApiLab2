
package org.example.apilab2.service.impl;

import org.example.apilab2.repository.ProgramaRepository;
import org.example.apilab2.service.ProgramaService;
import org.example.apilab2.service.dtos.ProgramaDto;
import org.example.apilab2.service.entities.Programa;
import org.example.apilab2.service.exceptions.NotFoundException;
import org.example.apilab2.service.mappers.ProgramaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProgramaServiceImpl implements ProgramaService {

    private final ProgramaRepository repo;
    private final ProgramaMapper mapper;

    public ProgramaServiceImpl(ProgramaRepository repo, ProgramaMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ProgramaDto crear(ProgramaDto dto) {
        Programa entity = mapper.toEntity(dto);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramaDto obtener(Long id) {
        Programa p = repo.findById(id).orElseThrow(() -> new NotFoundException("Programa no encontrado"));
        return mapper.toDto(p);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramaDto> listar(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public ProgramaDto actualizar(Long id, ProgramaDto dto) {
        Programa p = repo.findById(id).orElseThrow(() -> new NotFoundException("Programa no encontrado"));
        p.setNombre(dto.nombre);
        p.setEnfoquePedagogico(dto.enfoquePedagogico);
        p.setDuracion(dto.duracion);
        p.setInstitucion(dto.institucion);
        return mapper.toDto(repo.save(p));
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
