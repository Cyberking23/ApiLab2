
package org.example.apilab2.service.impl;

import org.example.apilab2.repository.*;
import org.example.apilab2.service.ParticipanteService;
import org.example.apilab2.service.dtos.ParticipanteDto;
import org.example.apilab2.service.entities.*;
import org.example.apilab2.service.exceptions.NotFoundException;
import org.example.apilab2.service.mappers.ParticipanteMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParticipanteServiceImpl implements ParticipanteService {

    private final ParticipanteRepository repo;
    private final ProgramaRepository programaRepo;
    private final ComunidadRepository comunidadRepo;
    private final ParticipanteMapper mapper;

    public ParticipanteServiceImpl(ParticipanteRepository repo, ProgramaRepository programaRepo,
                                   ComunidadRepository comunidadRepo, ParticipanteMapper mapper) {
        this.repo = repo;
        this.programaRepo = programaRepo;
        this.comunidadRepo = comunidadRepo;
        this.mapper = mapper;
    }

    @Override
    public ParticipanteDto crear(ParticipanteDto dto) {
        Participante p = mapper.toEntity(dto);
        if (dto.programaId != null) {
            Programa programa = programaRepo.findById(dto.programaId)
                    .orElseThrow(() -> new NotFoundException("Programa no encontrado"));
            p.setPrograma(programa);
        }
        if (dto.comunidadesIds != null && !dto.comunidadesIds.isEmpty()) {
            List<Comunidad> comunidades = comunidadRepo.findAllById(dto.comunidadesIds);
            if (comunidades.size() != dto.comunidadesIds.size()) {
                throw new NotFoundException("Alguna comunidad no existe");
            }
            p.setComunidades(comunidades);
        }
        p = repo.save(p);
        ParticipanteDto out = mapper.toDto(p);
        out.comunidadesIds = p.getComunidades().stream().map(Comunidad::getId).collect(Collectors.toList());
        return out;
    }

    @Override
    @Transactional(readOnly = true)
    public ParticipanteDto obtener(Long id) {
        Participante p = repo.findById(id).orElseThrow(() -> new NotFoundException("Participante no encontrado"));
        ParticipanteDto out = mapper.toDto(p);
        out.comunidadesIds = p.getComunidades().stream().map(Comunidad::getId).collect(Collectors.toList());
        return out;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParticipanteDto> listar(Long programaId, Long comunidadId, Pageable pageable) {
        Page<Participante> page = repo.findAll(pageable); // filtros simplificados
        return page.map(p -> {
            ParticipanteDto d = mapper.toDto(p);
            d.comunidadesIds = p.getComunidades().stream().map(Comunidad::getId).collect(Collectors.toList());
            return d;
        });
    }

    @Override
    public ParticipanteDto actualizar(Long id, ParticipanteDto dto) {
        Participante p = repo.findById(id).orElseThrow(() -> new NotFoundException("Participante no encontrado"));
        p.setNombre(dto.nombre);
        p.setEdad(dto.edad);
        p.setGenero(dto.genero);
        p.setNivelEducativo(dto.nivelEducativo);
        p.setIngresoFamiliar(dto.ingresoFamiliar);
        if (dto.programaId != null) {
            Programa programa = programaRepo.findById(dto.programaId)
                    .orElseThrow(() -> new NotFoundException("Programa no encontrado"));
            p.setPrograma(programa);
        } else {
            p.setPrograma(null);
        }
        if (dto.comunidadesIds != null) {
            List<Comunidad> comunidades = comunidadRepo.findAllById(dto.comunidadesIds);
            p.setComunidades(comunidades);
        }
        p = repo.save(p);
        ParticipanteDto out = mapper.toDto(p);
        out.comunidadesIds = p.getComunidades().stream().map(Comunidad::getId).collect(Collectors.toList());
        return out;
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
