package org.example.apilab2.service;

import org.example.apilab2.service.entities.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainerService {
    Trainer create(Trainer t);
    Trainer getById(Long id);
    Page<Trainer> findAll(Pageable pageable);
    Trainer update(Long id, Trainer updateData);
    void delete(Long id);
}
