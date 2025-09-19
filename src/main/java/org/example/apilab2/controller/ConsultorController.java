package org.example.apilab2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.apilab2.controller.request.ConsultorCreateRequest;
import org.example.apilab2.repository.ConsultorRepository;
import org.example.apilab2.repository.domain.Consultor;
import org.example.apilab2.service.ConsultorService;
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
@RequestMapping("/api/consultores")
@Tag(name = "Consultores", description = "CRUD de consultores")
public class ConsultorController {

    private final ConsultorService service;
    private final ConsultorRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear consultor", description = "Crea un nuevo consultor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Consultor creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public Consultor crear(@Valid @RequestBody ConsultorCreateRequest req) {
        return service.crear(req.nombre(), req.especializacion(), req.inActivoActualizado());
    }

    @GetMapping
    @Operation(summary = "Listar consultores", description = "Devuelve todos los consultores")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    public List<Consultor> listar() { return repo.findAll(); }
}
