package org.example.apilab2.service;

import org.example.apilab2.repository.*;
import org.example.apilab2.service.dtos.EvaluacionDto;
import org.example.apilab2.service.entities.*;
import org.example.apilab2.service.impl.EvaluacionServiceImpl;
import org.example.apilab2.service.mappers.EvaluacionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

class EvaluacionServiceTest {

    @Mock private EvaluacionRepository evaluacionRepo;
    @Mock private ParticipanteRepository participanteRepo;
    @Mock private EvaluacionMapper mapper;

    @InjectMocks private EvaluacionServiceImpl service;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void deberiaCrearEvaluacionParaParticipante() {
        EvaluacionDto dto = new EvaluacionDto();
        dto.participanteId = 1L;
        dto.puntajeTecnologiaInicial = 8.0;
        dto.puntajeTecnologiaFinal = 17.5;
        dto.observaciones = "Buen desempeño";

        Participante participante = new Participante();
        participante.setId(1L);

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setParticipante(participante);
        evaluacion.setFecha(LocalDate.now());

        when(mapper.toEntity(dto)).thenReturn(evaluacion);
        when(participanteRepo.findById(1L)).thenReturn(Optional.of(participante));
        when(evaluacionRepo.save(any())).thenReturn(evaluacion);
        when(mapper.toDto(any())).thenReturn(dto);

        EvaluacionDto resultado = service.crear(dto);

        assertThat(resultado.observaciones).isEqualTo("Buen desempeño");
        verify(evaluacionRepo, times(1)).save(any());
    }
}
