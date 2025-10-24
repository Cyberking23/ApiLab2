package org.example.apilab2.repository;

import org.example.apilab2.service.entities.Comunidad;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ComunidadRepositoryTest {

    @Autowired
    private ComunidadRepository repo;

    @Test
    void deberiaGuardarYLeerComunidad() {
        Comunidad comunidad = new Comunidad();
        comunidad.setNombre("Comunidad Esperanza");
        comunidad.setUbicacion("San Salvador");
        comunidad.setIndiceVulnerabilidad("Alta");

        Comunidad guardada = repo.save(comunidad);

        assertThat(guardada.getId()).isNotNull();
        assertThat(repo.findById(guardada.getId())).isPresent();
        assertThat(repo.findById(guardada.getId()).get().getUbicacion())
                .isEqualTo("San Salvador");
    }
}
