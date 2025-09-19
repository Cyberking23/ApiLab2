package org.example.apilab2.repository;

import org.example.apilab2.repository.domain.Participante;
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


class ParticipanteRepositoryTest {

    @Autowired
    private ParticipanteRepository participanteRepository;

    private Participante nuevo(String nombre, String email) {
        return Participante.builder()
                .nombre(nombre)
                .email(email)
                .genero("M")
                .nivelEducativo("Bachillerato")
                .ingresoFormal(true)
                .build();
    }

    @Test
    @DisplayName("Guarda y recupera un Participante por ID")
    void save_and_findById() {
        Participante guardado = participanteRepository.save(nuevo("Juan Perez", "juan@demo.com"));

        assertThat(guardado.getId()).isNotNull();

        Optional<Participante> hallado = participanteRepository.findById(guardado.getId());
        assertThat(hallado).isPresent();
        assertThat(hallado.get().getEmail()).isEqualTo("juan@demo.com");
        assertThat(hallado.get().getNombre()).isEqualTo("Juan Perez");
    }

    @Test
    @DisplayName("findByEmail: devuelve el Participante correcto")
    void findByEmail_returnsMatch() {
        participanteRepository.save(nuevo("Ana López", "ana@demo.com"));

        Optional<Participante> match = participanteRepository.findByEmail("ana@demo.com");
        assertThat(match).isPresent();
        assertThat(match.get().getNombre()).isEqualTo("Ana López");
    }

    @Test
    @DisplayName("findByEmail: vacío cuando el email no existe")
    void findByEmail_returnsEmptyWhenNotFound() {
        Optional<Participante> match = participanteRepository.findByEmail("no-existe@demo.com");
        assertThat(match).isEmpty();
    }
}
