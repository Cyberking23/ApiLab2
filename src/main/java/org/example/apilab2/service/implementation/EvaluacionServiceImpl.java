package org.example.apilab2.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.apilab2.controller.response.ImpactoProgramaResponse;
import org.example.apilab2.repository.EvaluacionRepository;
import org.example.apilab2.repository.ParticipanteRepository;
import org.example.apilab2.repository.ProgramaRepository;
import org.example.apilab2.repository.domain.Evaluacion;
import org.example.apilab2.repository.domain.Participante;
import org.example.apilab2.repository.domain.Programa;
import org.example.apilab2.service.EvaluacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service @RequiredArgsConstructor
public class EvaluacionServiceImpl implements EvaluacionService {

    private final EvaluacionRepository evaluacionRepo;
    private final ProgramaRepository programaRepo;
    private final ParticipanteRepository participanteRepo;

    @Override
    @Transactional
    public Evaluacion crear(Long programaId, Long participanteId, LocalDate fecha, Double puntaje, String obs) {
        Programa p = programaRepo.findById(programaId).orElseThrow();
        Participante a = participanteRepo.findById(participanteId).orElseThrow();
        if (evaluacionRepo.existsByProgramaAndParticipanteAndFecha(p, a, fecha)) {
            throw new IllegalArgumentException("Ya existe evaluación para esa fecha.");
        }
        Evaluacion e = Evaluacion.builder()
                .programa(p).participante(a).fecha(fecha).puntaje(puntaje).observaciones(obs).build();
        return evaluacionRepo.save(e);
    }

    @Override
    @Transactional(readOnly = true)
    public ImpactoProgramaResponse impacto(Long programaId) {
        List<Evaluacion> evals = evaluacionRepo.findByProgramaId(programaId);
        if (evals.isEmpty()) return new ImpactoProgramaResponse(programaId, 0, 0, 0);
        DoubleSummaryStatistics stats = evals.stream().mapToDouble(Evaluacion::getPuntaje).summaryStatistics();
        long aprobados = evals.stream().filter(e -> e.getPuntaje() >= 60).count();
        double promedio = stats.getAverage();
        double tasa = (aprobados * 100.0) / evals.size();
        return new ImpactoProgramaResponse(programaId, evals.size(), promedio, tasa);
    }
}
