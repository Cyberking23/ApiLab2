
package org.example.apilab2.controller;

import org.example.apilab2.service.ComunidadService;
import org.example.apilab2.service.dtos.ComunidadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comunidades")
public class ComunidadController {

    private final ComunidadService service;
    public ComunidadController(ComunidadService service) { this.service = service; }

    @PostMapping
    public ComunidadDto crear(@RequestBody ComunidadDto dto) { return service.crear(dto); }

    @GetMapping("/{id}")
    public ComunidadDto obtener(@PathVariable Long id) { return service.obtener(id); }

    @GetMapping
    public Page<ComunidadDto> listar(Pageable pageable) { return service.listar(pageable); }

    @PutMapping("/{id}")
    public ComunidadDto actualizar(@PathVariable Long id, @RequestBody ComunidadDto dto) { return service.actualizar(id, dto); }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
