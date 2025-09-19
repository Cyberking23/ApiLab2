package org.example.apilab2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.apilab2.controller.request.ParticipanteCreateRequest;
import org.example.apilab2.repository.ParticipanteRepository;
import org.example.apilab2.repository.domain.Participante;
import org.example.apilab2.service.ParticipanteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// OpenAPI
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participantes")
@Tag(name = "Participantes", description = "Alta y consulta de participantes")
public class ParticipanteController {
    private final ParticipanteService service;
    private final ParticipanteRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear participante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Participante creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public Participante crear(@Valid @RequestBody ParticipanteCreateRequest req) {
        return service.crear(req.nombre(), req.email(), req.genero(), req.nivelEducativo(), req.ingresoFormal());
    }

    @GetMapping
    @Operation(summary = "Listar participantes")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    public List<Participante> listar(){ return repo.findAll(); }
}
