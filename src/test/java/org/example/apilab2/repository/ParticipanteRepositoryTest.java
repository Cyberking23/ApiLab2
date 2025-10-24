package org.example.apilab2.repository;

import org.example.apilab2.service.entities.Participante;
import org.example.apilab2.service.entities.Programa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ParticipanteRepositoryTest {

    @Autowired
    private ParticipanteRepository participanteRepo;

    @Autowired
    private ProgramaRepository programaRepo;

    @Test
    void deberiaGuardarParticipanteConPrograma() {
        Programa programa = new Programa();
        programa.setNombre("Programa A");
        programa.setInstitucion("UNESCO");
        programaRepo.save(programa);

        Participante participante = new Participante();
        participante.setNombre("Juan Pérez");
        participante.setEdad(25);
        participante.setGenero("Masculino");
        participante.setNivelEducativo("Bachillerato");
        participante.setIngresoFamiliar(350.0);
        participante.setPrograma(programa);

        Participante guardado = participanteRepo.save(participante);

        assertThat(guardado.getId()).isNotNull();
        assertThat(participanteRepo.findById(guardado.getId())).isPresent();
        assertThat(guardado.getPrograma().getNombre()).isEqualTo("Programa A");
    }
}
