package org.example.apilab2.service;

import org.example.apilab2.repository.*;
import org.example.apilab2.service.dtos.ParticipanteDto;
import org.example.apilab2.service.entities.*;
import org.example.apilab2.service.impl.ParticipanteServiceImpl;
import org.example.apilab2.service.mappers.ParticipanteMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.*;

class ParticipanteServiceTest {

    @Mock private ParticipanteRepository participanteRepo;
    @Mock private ProgramaRepository programaRepo;
    @Mock private ComunidadRepository comunidadRepo;
    @Mock private ParticipanteMapper mapper;

    @InjectMocks private ParticipanteServiceImpl service;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void deberiaCrearParticipanteConProgramaYComunidades() {
        ParticipanteDto dto = new ParticipanteDto();
        dto.nombre = "Juan Perez";
        dto.programaId = 1L;
        dto.comunidadesIds = List.of(2L);

        Programa programa = new Programa();
        programa.setId(1L);
        Comunidad comunidad = new Comunidad();
        comunidad.setId(2L);

        Participante participante = new Participante();
        participante.setNombre("Juan Perez");

        when(mapper.toEntity(dto)).thenReturn(participante);
        when(programaRepo.findById(1L)).thenReturn(Optional.of(programa));
        when(comunidadRepo.findAllById(dto.comunidadesIds)).thenReturn(List.of(comunidad));
        when(participanteRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toDto(any())).thenReturn(dto);

        ParticipanteDto resultado = service.crear(dto);

        assertThat(resultado.nombre).isEqualTo("Juan Perez");
        verify(participanteRepo, times(1)).save(any());
    }
}
