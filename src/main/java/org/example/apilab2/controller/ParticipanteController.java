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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participantes")
public class ParticipanteController {
    private final ParticipanteService service;
    private final ParticipanteRepository repo;

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Participante crear(@Valid @RequestBody ParticipanteCreateRequest req) {
        return service.crear(req.nombre(), req.email(), req.genero(), req.nivelEducativo(), req.ingresoFormal());
    }

    @GetMapping public List<Participante> listar(){ return repo.findAll(); }
}
