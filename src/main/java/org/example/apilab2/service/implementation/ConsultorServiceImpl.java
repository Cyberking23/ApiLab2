package org.example.apilab2.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.apilab2.repository.ConsultorRepository;
import org.example.apilab2.repository.domain.Consultor;
import org.example.apilab2.service.ConsultorService;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class ConsultorServiceImpl implements ConsultorService {
    private final ConsultorRepository repo;

    @Override
    public Consultor crear(String nombre, String especializacion, Boolean inActivoActualizado) {
        return repo.save(Consultor.builder()
                .nombre(nombre).especializacion(especializacion).inActivoActualizado(inActivoActualizado).build());
    }
}
