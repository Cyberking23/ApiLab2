
package org.example.apilab2.controller;

import org.example.apilab2.service.ProgramaService;
import org.example.apilab2.service.dtos.ProgramaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/programas")
public class ProgramaController {

    private final ProgramaService service;
    public ProgramaController(ProgramaService service) { this.service = service; }

    @PostMapping
    public ProgramaDto crear(@RequestBody ProgramaDto dto) { return service.crear(dto); }

    @GetMapping("/{id}")
    public ProgramaDto obtener(@PathVariable Long id) { return service.obtener(id); }

    @GetMapping
    public Page<ProgramaDto> listar(Pageable pageable) { return service.listar(pageable); }

    @PutMapping("/{id}")
    public ProgramaDto actualizar(@PathVariable Long id, @RequestBody ProgramaDto dto) { return service.actualizar(id, dto); }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
