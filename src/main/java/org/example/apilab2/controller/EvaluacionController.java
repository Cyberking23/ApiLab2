
package org.example.apilab2.controller;

import org.example.apilab2.service.EvaluacionService;
import org.example.apilab2.service.dtos.EvaluacionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService service;
    public EvaluacionController(EvaluacionService service) { this.service = service; }

    @PostMapping
    public EvaluacionDto crear(@RequestBody EvaluacionDto dto) { return service.crear(dto); }

    @GetMapping("/{id}")
    public EvaluacionDto obtener(@PathVariable Long id) { return service.obtener(id); }

    @GetMapping
    public Page<EvaluacionDto> listar(@RequestParam(required = false) Long participanteId, Pageable pageable) {
        return service.listar(participanteId, pageable);
    }

    @PutMapping("/{id}")
    public EvaluacionDto actualizar(@PathVariable Long id, @RequestBody EvaluacionDto dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
