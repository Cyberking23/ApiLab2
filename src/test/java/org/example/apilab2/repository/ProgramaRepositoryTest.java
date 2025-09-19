package org.example.apilab2.repository;

import org.example.apilab2.repository.domain.Consultor;
import org.example.apilab2.repository.domain.Participante;
import org.example.apilab2.repository.domain.Programa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@EnableJpaRepositories(basePackages = "org.example.apilab2")
@EntityScan(basePackages = "org.example.apilab2")
class ProgramaRepositoryTest {

    @Autowired private ProgramaRepository programaRepository;
    @Autowired private ConsultorRepository consultorRepository;
    @Autowired private ParticipanteRepository participanteRepository;

    private Consultor nuevoConsultor() {
        return consultorRepository.save(
                Consultor.builder()
                        .nombre("Ada Lovelace")
                        .especializacion("Backend")
                        .inActivoActualizado(true)
                        .build()
        );
    }

    private Programa nuevoPrograma(Consultor c) {
        return Programa.builder()
                .nombre("Bootcamp Java")
                .enfoque("basico")
                .duracion("40h")
                .institucion("UDB")
                .consultor(c)
                .build();
    }

    @Test
    @DisplayName("Guarda y recupera un Programa por ID")
    void save_and_findById() {
        Consultor c = nuevoConsultor();
        Programa guardado = programaRepository.save(nuevoPrograma(c));

        assertThat(guardado.getId()).isNotNull();

        Optional<Programa> hallado = programaRepository.findById(guardado.getId());
        assertThat(hallado).isPresent();
        assertThat(hallado.get().getNombre()).isEqualTo("Bootcamp Java");
        assertThat(hallado.get().getConsultor().getNombre()).isEqualTo("Ada Lovelace");
    }

    @Test
    @DisplayName("ManyToMany: agrega un Participante a un Programa")
    void add_participante_to_programa() {
        Consultor c = nuevoConsultor();
        Programa p = programaRepository.save(nuevoPrograma(c));

        Participante part = participanteRepository.save(
                Participante.builder()
                        .nombre("Grace Hopper")
                        .email("grace@demo.com")
                        .genero("F")
                        .nivelEducativo("Universitario")
                        .ingresoFormal(true)
                        .build()
        );

        p.getParticipantes().add(part);
        Programa actualizado = programaRepository.save(p);

        assertThat(actualizado.getParticipantes()).hasSize(1);
        assertThat(actualizado.getParticipantes().iterator().next().getEmail())
                .isEqualTo("grace@demo.com");
    }
}

