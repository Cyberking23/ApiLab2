package org.example.apilab2.service.impl;

import org.example.apilab2.service.ParticipantService;
import org.example.apilab2.service.entities.Participant;
import org.example.apilab2.service.entities.Trainer;
import org.example.apilab2.repository.ParticipantRepository;
import org.example.apilab2.repository.TrainerRepository;
import org.example.apilab2.service.exceptions.ConflictException;
import org.example.apilab2.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final TrainerRepository trainerRepository;

    @Override
    public Participant create(Participant p, Long trainerId) {
        if (participantRepository.existsByEmail(p.getEmail())) throw new ConflictException("El email ya existe");
        Trainer t = trainerRepository.findById(trainerId).orElseThrow(() -> new NotFoundException("Capacitador no encontrado"));
        p.setTrainer(t);
        return participantRepository.save(p);
    }
    @Override @Transactional(readOnly=true)
    public Participant getById(Long id) { return participantRepository.findById(id).orElseThrow(() -> new NotFoundException("Participante no encontrado")); }
    @Override @Transactional(readOnly=true)
    public Page<Participant> findAll(Pageable pageable) { return participantRepository.findAll(pageable); }
    @Override
    public Participant update(Long id, Participant data) {
        var e = getById(id);
        e.setName(data.getName()); e.setAge(data.getAge()); e.setGender(data.getGender());
        e.setEducationLevel(data.getEducationLevel()); e.setFamilyIncome(data.getFamilyIncome());
        return e;
    }
    @Override public void delete(Long id) { participantRepository.delete(getById(id)); }
}
