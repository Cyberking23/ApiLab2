package org.example.apilab2.service.impl;

import org.example.apilab2.service.EvaluationService;
import org.example.apilab2.service.entities.Evaluation;
import org.example.apilab2.service.entities.Participant;
import org.example.apilab2.repository.EvaluationRepository;
import org.example.apilab2.repository.ParticipantRepository;
import org.example.apilab2.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationRepository repo;
    private final ParticipantRepository participantRepository;
    @Override public Evaluation create(Evaluation e, Long participantId) {
        Participant p = participantRepository.findById(participantId).orElseThrow(() -> new NotFoundException("Participante no encontrado"));
        e.setParticipant(p);
        return repo.save(e);
    }
    @Override @Transactional(readOnly=true) public Evaluation getById(Long id) { return repo.findById(id).orElseThrow(() -> new NotFoundException("Evaluación no encontrada")); }
    @Override @Transactional(readOnly=true) public Page<Evaluation> findAll(Pageable pageable) { return repo.findAll(pageable); }
    @Override public Evaluation update(Long id, Evaluation data) { var e = getById(id); e.setDate(data.getDate()); e.setScore(data.getScore()); e.setNotes(data.getNotes()); return e; }
    @Override public void delete(Long id) { repo.delete(getById(id)); }
}
