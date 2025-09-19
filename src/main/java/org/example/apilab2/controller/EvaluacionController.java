package org.example.apilab2.controller;

import lombok.RequiredArgsConstructor;
import org.example.apilab2.controller.response.EvaluacionResponse;
import org.example.apilab2.controller.response.ParticipanteResponse;
import org.example.apilab2.controller.response.ProgramaResponse;
import org.example.apilab2.repository.EvaluacionRepository;
import org.example.apilab2.repository.domain.Evaluacion;
import org.example.apilab2.repository.domain.Participante;
import org.example.apilab2.repository.domain.Programa;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// OpenAPI
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/evaluaciones")
@Tag(name = "Evaluaciones", description = "Consulta de evaluaciones")
public class EvaluacionController {

    private final EvaluacionRepository repo;

    @GetMapping
    @Operation(summary = "Listar evaluaciones")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    public List<EvaluacionResponse> listar() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener evaluación por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No encontrada")
    })
    public EvaluacionResponse get(@Parameter(description = "ID de la evaluación")
                                  @PathVariable Long id) {
        return repo.findById(id).map(this::toResponse).orElseThrow();
    }

    @GetMapping("/programa/{programaId}")
    @Operation(summary = "Listar evaluaciones por programa")
    public List<EvaluacionResponse> porPrograma(@Parameter(description = "ID del programa")
                                                @PathVariable Long programaId) {
        return repo.findByProgramaId(programaId).stream().map(this::toResponse).toList();
    }

    @GetMapping("/participante/{participanteId}")
    @Operation(summary = "Listar evaluaciones por participante")
    public List<EvaluacionResponse> porParticipante(@Parameter(description = "ID del participante")
                                                    @PathVariable Long participanteId) {
        return repo.findByParticipanteId(participanteId).stream().map(this::toResponse).toList();
    }

    // ---- helpers ----
    private EvaluacionResponse toResponse(Evaluacion e) {
        return new EvaluacionResponse(
                e.getId(),
                e.getFecha(),
                e.getPuntaje(),
                e.getObservaciones(),
                toParticipanteResponse(e.getParticipante()),
                toProgramaResponse(e.getPrograma())
        );
    }

    private ParticipanteResponse toParticipanteResponse(Participante p) {
        return new ParticipanteResponse(
                p.getId(), p.getNombre(), p.getEmail(), p.getGenero(),
                p.getNivelEducativo(), p.getIngresoFormal()
        );
    }

    private ProgramaResponse toProgramaResponse(Programa pr) {
        return new ProgramaResponse(
                pr.getId(), pr.getNombre(), pr.getEnfoque(), pr.getDuracion(),
                pr.getInstitucion(),
                pr.getConsultor() != null ? pr.getConsultor().getId() : null
        );
    }
}
