
package org.example.apilab2.controller;

import org.example.apilab2.service.ParticipanteService;
import org.example.apilab2.service.dtos.ParticipanteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/participantes")
public class ParticipanteController {

    private final ParticipanteService service;
    public ParticipanteController(ParticipanteService service) { this.service = service; }

    @PostMapping
    public ParticipanteDto crear(@RequestBody ParticipanteDto dto) { return service.crear(dto); }

    @GetMapping("/{id}")
    public ParticipanteDto obtener(@PathVariable Long id) { return service.obtener(id); }

    @GetMapping
    public Page<ParticipanteDto> listar(@RequestParam(required = false) Long programaId,
                                        @RequestParam(required = false) Long comunidadId,
                                        Pageable pageable) {
        return service.listar(programaId, comunidadId, pageable);
    }

    @PutMapping("/{id}")
    public ParticipanteDto actualizar(@PathVariable Long id, @RequestBody ParticipanteDto dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
