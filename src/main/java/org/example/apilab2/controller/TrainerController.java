package org.example.apilab2.controller;

import org.example.apilab2.service.TrainerService;
import org.example.apilab2.service.entities.Trainer;
import org.example.apilab2.service.dtos.TrainerDtos;
import org.example.apilab2.service.mappers.TrainerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService service;
    private final TrainerMapper mapper;

    // Crear entrenador
    @PostMapping
    public ResponseEntity<TrainerDtos.Response> create(@Valid @RequestBody TrainerDtos.Create req) {
        Trainer entity = mapper.toEntity(req);
        var created = service.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    // Obtener entrenador por ID
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDtos.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.getById(id)));
    }

    // Obtener todos los entrenadores con paginación y orden
    @GetMapping
    public ResponseEntity<Page<TrainerDtos.Response>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String sort) {
        var parts = sort.split(",");
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(parts[1]), parts[0]));
        return ResponseEntity.ok(service.findAll(pageable).map(mapper::toResponse));
    }

    // Actualizar entrenador por ID
    @PutMapping("/{id}")
    public ResponseEntity<TrainerDtos.Response> update(@PathVariable Long id, @Valid @RequestBody TrainerDtos.Update req) {
        var updated = service.update(
                id,
                mapper.toEntity(new TrainerDtos.Create(req.name(), req.experienceYears(), req.academicLevel()))
        );
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    // Eliminar entrenador por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
