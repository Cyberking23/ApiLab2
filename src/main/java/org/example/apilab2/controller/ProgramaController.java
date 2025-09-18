package org.example.apilab2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.apilab2.controller.request.EvaluacionCreateRequest;
import org.example.apilab2.controller.request.ProgramaCreateRequest;
import org.example.apilab2.controller.response.ImpactoProgramaResponse;
import org.example.apilab2.repository.ProgramaRepository;
import org.example.apilab2.repository.domain.Evaluacion;
import org.example.apilab2.repository.domain.Programa;
import org.example.apilab2.service.EvaluacionService;
import org.example.apilab2.service.ProgramaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/programas")
public class ProgramaController {
    private final ProgramaService programaService;
    private final EvaluacionService evaluacionService;
    private final ProgramaRepository programaRepo;

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Programa crear(@Valid @RequestBody ProgramaCreateRequest req){
        return programaService.crear(req.nombre(), req.enfoque(), req.duracion(), req.institucion(), req.consultorId());
    }

    @GetMapping("/{id}")
    public Programa get(@PathVariable Long id){ return programaRepo.findById(id).orElseThrow(); }

    @PostMapping("/{programaId}/inscripciones/{participanteId}")
    public Programa inscribir(@PathVariable Long programaId, @PathVariable Long participanteId){
        return programaService.inscribirParticipante(programaId, participanteId);
    }

    @PostMapping("/{programaId}/evaluaciones") @ResponseStatus(HttpStatus.CREATED)
    public Evaluacion evaluar(@PathVariable Long programaId, @Valid @RequestBody EvaluacionCreateRequest req){
        return evaluacionService.crear(programaId, req.participanteId(), req.fecha(), req.puntaje(), req.observaciones());
    }

    @GetMapping("/{programaId}/impacto")
    public ImpactoProgramaResponse impacto(@PathVariable Long programaId){
        return evaluacionService.impacto(programaId);
    }
}
