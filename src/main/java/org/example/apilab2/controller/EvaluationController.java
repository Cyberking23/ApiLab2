package org.example.apilab2.controller;

import org.example.apilab2.service.EvaluationService;
import org.example.apilab2.service.entities.Evaluation;
import org.example.apilab2.service.dtos.EvaluationDtos;
import org.example.apilab2.service.mappers.EvaluationMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService service;
    private final EvaluationMapper mapper;

    // Crear evaluación
    @PostMapping
    public ResponseEntity<EvaluationDtos.Response> create(@Valid @RequestBody EvaluationDtos.Create req) {
        Evaluation entity = mapper.toEntity(req);
        var created = service.create(entity, req.participantId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    // Obtener evaluación por ID
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationDtos.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.getById(id)));
    }

    // Obtener todas las evaluaciones con paginación y orden
    @GetMapping
    public ResponseEntity<Page<EvaluationDtos.Response>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String sort) {
        var parts = sort.split(",");
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(parts[1]), parts[0]));
        return ResponseEntity.ok(service.findAll(pageable).map(mapper::toResponse));
    }

    // Actualizar evaluación por ID
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationDtos.Response> update(@PathVariable Long id, @Valid @RequestBody EvaluationDtos.Update req) {
        var updated = service.update(id, mapper.toEntity(new EvaluationDtos.Create(req.date(), req.score(), req.notes(), 0L)));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    // Eliminar evaluación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
