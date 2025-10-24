
package org.example.apilab2.service.impl;

import org.example.apilab2.repository.*;
import org.example.apilab2.service.EvaluacionService;
import org.example.apilab2.service.dtos.EvaluacionDto;
import org.example.apilab2.service.entities.*;
import org.example.apilab2.service.exceptions.NotFoundException;
import org.example.apilab2.service.mappers.EvaluacionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EvaluacionServiceImpl implements EvaluacionService {

    private final EvaluacionRepository repo;
    private final ParticipanteRepository participanteRepo;
    private final EvaluacionMapper mapper;

    public EvaluacionServiceImpl(EvaluacionRepository repo, ParticipanteRepository participanteRepo, EvaluacionMapper mapper) {
        this.repo = repo;
        this.participanteRepo = participanteRepo;
        this.mapper = mapper;
    }

    @Override
    public EvaluacionDto crear(EvaluacionDto dto) {
        Evaluacion e = mapper.toEntity(dto);
        Participante participante = participanteRepo.findById(dto.participanteId)
                .orElseThrow(() -> new NotFoundException("Participante no encontrado"));
        e.setParticipante(participante);
        return mapper.toDto(repo.save(e));
    }

    @Override
    @Transactional(readOnly = true)
    public EvaluacionDto obtener(Long id) {
        Evaluacion e = repo.findById(id).orElseThrow(() -> new NotFoundException("Evaluación no encontrada"));
        return mapper.toDto(e);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluacionDto> listar(Long participanteId, Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public EvaluacionDto actualizar(Long id, EvaluacionDto dto) {
        Evaluacion e = repo.findById(id).orElseThrow(() -> new NotFoundException("Evaluación no encontrada"));
        e.setFecha(dto.fecha);
        e.setPuntajeTecnologiaInicial(dto.puntajeTecnologiaInicial);
        e.setPuntajeTecnologiaFinal(dto.puntajeTecnologiaFinal);
        e.setObservaciones(dto.observaciones);
        if (dto.participanteId != null) {
            Participante p = participanteRepo.findById(dto.participanteId)
                    .orElseThrow(() -> new NotFoundException("Participante no encontrado"));
            e.setParticipante(p);
        }
        return mapper.toDto(repo.save(e));
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
