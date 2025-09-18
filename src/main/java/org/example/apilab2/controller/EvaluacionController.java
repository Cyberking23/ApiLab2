package org.example.apilab2.controller;

import lombok.RequiredArgsConstructor;
import org.example.apilab2.repository.EvaluacionRepository;
import org.example.apilab2.repository.domain.Evaluacion;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    private final EvaluacionRepository repo;

    @GetMapping
    public List<Evaluacion> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Evaluacion get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @GetMapping("/programa/{programaId}")
    public List<Evaluacion> porPrograma(@PathVariable Long programaId) {
        return repo.findByProgramaId(programaId);
    }

    @GetMapping("/participante/{participanteId}")
    public List<Evaluacion> porParticipante(@PathVariable Long participanteId) {
        return repo.findByParticipanteId(participanteId);
    }
}
