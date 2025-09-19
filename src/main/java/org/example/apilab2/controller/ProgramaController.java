package org.example.apilab2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.apilab2.controller.request.EvaluacionCreateRequest;
import org.example.apilab2.controller.request.ProgramaCreateRequest;
import org.example.apilab2.controller.response.EvaluacionResponse;
import org.example.apilab2.controller.response.ImpactoProgramaResponse;
import org.example.apilab2.controller.response.ParticipanteResponse;
import org.example.apilab2.controller.response.ProgramaResponse;
import org.example.apilab2.repository.ProgramaRepository;
import org.example.apilab2.repository.domain.Evaluacion;
import org.example.apilab2.repository.domain.Participante;
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
    public ProgramaResponse crear(@Valid @RequestBody ProgramaCreateRequest req){
        Programa p = programaService.crear(req.nombre(), req.enfoque(), req.duracion(), req.institucion(), req.consultorId());
        return toProgramaResponse(p);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener programa por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    public ProgramaResponse get(@Parameter(description = "ID del programa")
                                @PathVariable Long id){
        Programa p = programaRepo.findById(id).orElseThrow();
        return toProgramaResponse(p);
    }

    @PostMapping("/{programaId}/inscripciones/{participanteId}")
    @Operation(summary = "Inscribir participante en un programa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inscripción realizada"),
            @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    public ProgramaResponse inscribir(@Parameter(description = "ID del programa") @PathVariable Long programaId,
                                      @Parameter(description = "ID del participante") @PathVariable Long participanteId){
        Programa p = programaService.inscribirParticipante(programaId, participanteId);
        return toProgramaResponse(p);
    }

    @PostMapping("/{programaId}/evaluaciones")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear evaluación para un participante en el programa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evaluación creada"),
            @ApiResponse(responseCode = "404", description = "No encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public EvaluacionResponse evaluar(@Parameter(description = "ID del programa") @PathVariable Long programaId,
                                      @Valid @RequestBody EvaluacionCreateRequest req){
        Evaluacion e = evaluacionService.crear(programaId, req.participanteId(), req.fecha(), req.puntaje(), req.observaciones());
        // Reuso del helper de EvaluacionController si lo extraes a un componente; aquí lo rearmo rápido:
        return new EvaluacionResponse(
                e.getId(), e.getFecha(), e.getPuntaje(), e.getObservaciones(),
                new ParticipanteResponse(
                        e.getParticipante().getId(),
                        e.getParticipante().getNombre(),
                        e.getParticipante().getEmail(),
                        e.getParticipante().getGenero(),
                        e.getParticipante().getNivelEducativo(),
                        e.getParticipante().getIngresoFormal()
                ),
                new ProgramaResponse(
                        e.getPrograma().getId(),
                        e.getPrograma().getNombre(),
                        e.getPrograma().getEnfoque(),
                        e.getPrograma().getDuracion(),
                        e.getPrograma().getInstitucion(),
                        e.getPrograma().getConsultor() != null ? e.getPrograma().getConsultor().getId() : null
                )
        );
    }

    @GetMapping("/{programaId}/impacto")
    @Operation(summary = "Obtener métricas de impacto del programa")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    public ImpactoProgramaResponse impacto(@Parameter(description = "ID del programa") @PathVariable Long programaId){
        return evaluacionService.impacto(programaId);
    }

    private ProgramaResponse toProgramaResponse(Programa pr) {
        return new ProgramaResponse(
                pr.getId(), pr.getNombre(), pr.getEnfoque(), pr.getDuracion(),
                pr.getInstitucion(),
                pr.getConsultor() != null ? pr.getConsultor().getId() : null
        );
    }
}
