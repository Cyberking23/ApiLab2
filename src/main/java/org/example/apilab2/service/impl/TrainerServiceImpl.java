package org.example.apilab2.service.impl;

import org.example.apilab2.service.TrainerService;
import org.example.apilab2.service.entities.Trainer;
import org.example.apilab2.repository.TrainerRepository;
import org.example.apilab2.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository repo;
    @Override public Trainer create(Trainer t) { return repo.save(t); }
    @Override @Transactional(readOnly=true) public Trainer getById(Long id) { return repo.findById(id).orElseThrow(() -> new NotFoundException("Capacitador no encontrado")); }
    @Override @Transactional(readOnly=true) public Page<Trainer> findAll(Pageable pageable) { return repo.findAll(pageable); }
    @Override public Trainer update(Long id, Trainer data) { var e = getById(id); e.setName(data.getName()); e.setExperienceYears(data.getExperienceYears()); e.setAcademicLevel(data.getAcademicLevel()); return e; }
    @Override public void delete(Long id) { repo.delete(getById(id)); }
}
