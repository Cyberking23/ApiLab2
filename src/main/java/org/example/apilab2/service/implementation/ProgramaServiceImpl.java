package org.example.apilab2.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.apilab2.repository.ConsultorRepository;
import org.example.apilab2.repository.ParticipanteRepository;
import org.example.apilab2.repository.ProgramaRepository;
import org.example.apilab2.repository.domain.Consultor;
import org.example.apilab2.repository.domain.Participante;
import org.example.apilab2.repository.domain.Programa;
import org.example.apilab2.service.ProgramaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class ProgramaServiceImpl implements ProgramaService {
    private final ProgramaRepository programaRepo;
    private final ConsultorRepository consultorRepo;
    private final ParticipanteRepository participanteRepo;

    @Override
    @Transactional
    public Programa crear(String nombre, String enfoque, String duracion, String institucion, Long consultorId) {
        Consultor c = consultorRepo.findById(consultorId).orElseThrow();
        Programa p = Programa.builder()
                .nombre(nombre).enfoque(enfoque).duracion(duracion).institucion(institucion).consultor(c).build();
        return programaRepo.save(p);
    }

    @Override
    @Transactional
    public Programa inscribirParticipante(Long programaId, Long participanteId) {
        Programa p = programaRepo.findById(programaId).orElseThrow();
        Participante a = participanteRepo.findById(participanteId).orElseThrow();
        p.getParticipantes().add(a);
        return programaRepo.save(p);
    }
}
