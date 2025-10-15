package org.example.apilab2.service;

import org.example.apilab2.service.entities.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EvaluationService {
    Evaluation create(Evaluation e, Long participantId);
    Evaluation getById(Long id);
    Page<Evaluation> findAll(Pageable pageable);
    Evaluation update(Long id, Evaluation updateData);
    void delete(Long id);
}
