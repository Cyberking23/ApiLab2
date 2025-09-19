package org.example.apilab2.service;

import org.example.apilab2.repository.ConsultorRepository;
import org.example.apilab2.repository.ParticipanteRepository;
import org.example.apilab2.repository.ProgramaRepository;
import org.example.apilab2.repository.domain.Consultor;
import org.example.apilab2.repository.domain.Participante;
import org.example.apilab2.repository.domain.Programa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class ProgramaServiceIT {

    @Autowired private ProgramaService programaService;
    @Autowired private ProgramaRepository programaRepository;
    @Autowired private ParticipanteRepository participanteRepository;
    @Autowired private ConsultorRepository consultorRepository;

    // ------- helpers -------
    private Consultor nuevoConsultor(String nombre) {
        return consultorRepository.save(
                Consultor.builder()
                        .nombre(nombre)
                        .especializacion("Backend")
                        .inActivoActualizado(true)
                        .build()
        );
    }

    private Participante nuevoParticipante(String nombre, String email) {
        return participanteRepository.save(
                Participante.builder()
                        .nombre(nombre)
                        .email(email)
                        .genero("M")
                        .nivelEducativo("Bachillerato")
                        .ingresoFormal(true)
                        .build()
        );
    }

    // ----------------- tests -----------------

    @Test
    @DisplayName("crear(nombre,enfoque,duracion,institucion,consultorId) persiste y devuelve el Programa")
    void crearPrograma_ok() {
        // dado un consultor existente
        Consultor c = nuevoConsultor("Ada Lovelace");

        // cuando creo el programa usando la FIRMA REAL del servicio
        Programa creado = programaService.crear(
                "Bootcamp Java",   // nombre
                "basico",          // enfoque
                "40h",             // duracion
                "UDB",             // institucion
                c.getId()          // consultorId
        );

        // entonces
        assertThat(creado).isNotNull();
        assertThat(creado.getId()).isNotNull();
        assertThat(creado.getNombre()).isEqualTo("Bootcamp Java");
        assertThat(creado.getConsultor()).isNotNull();
        assertThat(creado.getConsultor().getId()).isEqualTo(c.getId());

        // persiste de verdad
        Programa enBD = programaRepository.findById(creado.getId()).orElseThrow();
        assertThat(enBD.getNombre()).isEqualTo("Bootcamp Java");
        assertThat(enBD.getConsultor().getNombre()).isEqualTo("Ada Lovelace");
    }

    @Test
    @DisplayName("inscribirParticipante(programaId, participanteId) vincula el participante al programa")
    void inscribirParticipante_ok() {
        // armado: consultor + programa creado con la firma correcta
        Consultor c = nuevoConsultor("Grace Hopper");
        Programa programa = programaService.crear("API Spring", "intermedio", "30h", "UDB", c.getId());

        // y un participante existente
        Participante p = nuevoParticipante("Linus", "linus@test.com");

        // acción
        Programa actualizado = programaService.inscribirParticipante(programa.getId(), p.getId());

        // verificación
        assertThat(actualizado.getParticipantes())
                .isNotEmpty()
                .anyMatch(part -> "linus@test.com".equals(part.getEmail()));

        // y también desde repositorio (lectura fresca)
        Programa recargado = programaRepository.findById(programa.getId()).orElseThrow();
        assertThat(recargado.getParticipantes())
                .isNotEmpty()
                .anyMatch(part -> "linus@test.com".equals(part.getEmail()));
    }
}
