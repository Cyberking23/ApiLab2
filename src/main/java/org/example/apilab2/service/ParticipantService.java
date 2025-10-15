package org.example.apilab2.service;

import org.example.apilab2.service.entities.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipantService {
    Participant create(Participant p, Long trainerId);
    Participant getById(Long id);
    Page<Participant> findAll(Pageable pageable);
    Participant update(Long id, Participant updateData);
    void delete(Long id);
}
