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

// OpenAPI
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/programas")
@Tag(name = "Programas", description = "Gestión de programas y su impacto")
public class ProgramaController {
    private final ProgramaService programaService;
    private final EvaluacionService evaluacionService;
    private final ProgramaRepository programaRepo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear programa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Programa creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public Programa crear(@Valid @RequestBody ProgramaCreateRequest req){
        return programaService.crear(req.nombre(), req.enfoque(), req.duracion(), req.institucion(), req.consultorId());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener programa por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    public Programa get(@Parameter(description = "ID del programa")
                        @PathVariable Long id){
        return programaRepo.findById(id).orElseThrow();
    }

    @PostMapping("/{programaId}/inscripciones/{participanteId}")
    @Operation(summary = "Inscribir participante en un programa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inscripción realizada"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    public Programa inscribir(@Parameter(description = "ID del programa") @PathVariable Long programaId,
                              @Parameter(description = "ID del participante") @PathVariable Long participanteId){
        return programaService.inscribirParticipante(programaId, participanteId);
    }

    @PostMapping("/{programaId}/evaluaciones")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear evaluación para un participante en el programa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evaluación creada"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public Evaluacion evaluar(@Parameter(description = "ID del programa") @PathVariable Long programaId,
                              @Valid @RequestBody EvaluacionCreateRequest req){
        return evaluacionService.crear(programaId, req.participanteId(), req.fecha(), req.puntaje(), req.observaciones());
    }

    @GetMapping("/{programaId}/impacto")
    @Operation(summary = "Obtener métricas de impacto del programa")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    public ImpactoProgramaResponse impacto(@Parameter(description = "ID del programa") @PathVariable Long programaId){
        return evaluacionService.impacto(programaId);
    }
}
