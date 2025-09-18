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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consultores")
public class ConsultorController {

    private final ConsultorService service;
    private final ConsultorRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consultor crear(@Valid @RequestBody ConsultorCreateRequest req) {
        return service.crear(req.nombre(), req.especializacion(), req.inActivoActualizado());
    }

    @GetMapping public List<Consultor> listar() { return repo.findAll(); }
}
