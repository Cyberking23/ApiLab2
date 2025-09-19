package org.example.apilab2.controller;

import lombok.RequiredArgsConstructor;
import org.example.apilab2.repository.EvaluacionRepository;
import org.example.apilab2.repository.domain.Evaluacion;
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
    public List<Evaluacion> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener evaluación por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No encontrada")
    })
    public Evaluacion get(@Parameter(description = "ID de la evaluación")
                          @PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @GetMapping("/programa/{programaId}")
    @Operation(summary = "Listar evaluaciones por programa")
    public List<Evaluacion> porPrograma(@Parameter(description = "ID del programa")
                                        @PathVariable Long programaId) {
        return repo.findByProgramaId(programaId);
    }

    @GetMapping("/participante/{participanteId}")
    @Operation(summary = "Listar evaluaciones por participante")
    public List<Evaluacion> porParticipante(@Parameter(description = "ID del participante")
                                            @PathVariable Long participanteId) {
        return repo.findByParticipanteId(participanteId);
    }
}
