package org.example.apilab2.repository;

import org.example.apilab2.service.entities.Evaluacion;
import org.example.apilab2.service.entities.Participante;
import org.example.apilab2.service.entities.Programa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EvaluacionRepositoryTest {

    @Autowired
    private EvaluacionRepository evaluacionRepo;

    @Autowired
    private ParticipanteRepository participanteRepo;

    @Autowired
    private ProgramaRepository programaRepo;

    @Test
    void deberiaGuardarEvaluacionDeParticipante() {
        Programa programa = new Programa();
        programa.setNombre("Programa TIC Avanzado");
        programaRepo.save(programa);

        Participante participante = new Participante();
        participante.setNombre("Ana López");
        participante.setPrograma(programa);
        participanteRepo.save(participante);

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setFecha(LocalDate.now());
        evaluacion.setPuntajeTecnologiaInicial(10.0);
        evaluacion.setPuntajeTecnologiaFinal(18.5);
        evaluacion.setObservaciones("Excelente avance");
        evaluacion.setParticipante(participante);

        Evaluacion guardada = evaluacionRepo.save(evaluacion);

        assertThat(guardada.getId()).isNotNull();
        assertThat(evaluacionRepo.findById(guardada.getId())).isPresent();
        assertThat(guardada.getParticipante().getNombre()).isEqualTo("Ana López");
    }
}
