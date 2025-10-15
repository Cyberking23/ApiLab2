package org.example.apilab2.controller;

import org.example.apilab2.service.ParticipantService;
import org.example.apilab2.service.entities.Participant;
import org.example.apilab2.service.dtos.ParticipantDtos;
import org.example.apilab2.service.mappers.ParticipantMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService service;
    private final ParticipantMapper mapper;

    // Crear participante
    @PostMapping
    public ResponseEntity<ParticipantDtos.Response> create(@Valid @RequestBody ParticipantDtos.Create req) {
        Participant entity = mapper.toEntity(req);
        var created = service.create(entity, req.trainerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(created));
    }

    // Obtener participante por ID
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDtos.Response> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(service.getById(id)));
    }

    // Obtener todos los participantes con paginación y orden
    @GetMapping
    public ResponseEntity<Page<ParticipantDtos.Response>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String sort) {
        var parts = sort.split(",");
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(parts[1]), parts[0]));
        return ResponseEntity.ok(service.findAll(pageable).map(mapper::toResponse));
    }

    // Actualizar participante por ID
    @PutMapping("/{id}")
    public ResponseEntity<ParticipantDtos.Response> update(@PathVariable Long id, @Valid @RequestBody ParticipantDtos.Update req) {
        var toUpdate = new Participant();
        mapper.update(toUpdate, req);
        var updated = service.update(id, toUpdate);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    // Eliminar participante por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
