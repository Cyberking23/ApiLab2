package org.example.apilab2.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.apilab2.repository.ParticipanteRepository;
import org.example.apilab2.repository.domain.Participante;
import org.example.apilab2.service.ParticipanteService;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class ParticipanteServiceImpl implements ParticipanteService {
    private final ParticipanteRepository repo;
    @Override
    public Participante crear(String nombre, String email, String genero, String nivelEducativo, Boolean ingresoFormal) {
        return repo.save(Participante.builder()
                .nombre(nombre).email(email).genero(genero).nivelEducativo(nivelEducativo).ingresoFormal(ingresoFormal).build());
    }
}
