package org.example.apilab2.repository;

import org.example.apilab2.service.entities.Programa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProgramaRepositoryTest {

    @Autowired
    private ProgramaRepository repo;

    @Test
    void deberiaGuardarYLeerPrograma() {
        Programa programa = new Programa();
        programa.setNombre("Programa Alfabetización Digital");
        programa.setDuracion("3 meses");
        programa.setInstitucion("CEPAL");

        Programa guardado = repo.save(programa);

        assertThat(guardado.getId()).isNotNull();
        assertThat(repo.findById(guardado.getId())).isPresent();
        assertThat(repo.findById(guardado.getId()).get().getNombre())
                .isEqualTo("Programa Alfabetización Digital");
    }
}
