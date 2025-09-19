package org.example.apilab2.repository;

import org.example.apilab2.repository.domain.Consultor;
import org.example.apilab2.repository.domain.Evaluacion;
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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@EnableJpaRepositories(basePackages = "org.example.apilab2")
@EntityScan(basePackages = "org.example.apilab2")

class EvaluacionRepositoryTest {

    @Autowired private EvaluacionRepository evaluacionRepository;
    @Autowired private ProgramaRepository programaRepository;
    @Autowired private ParticipanteRepository participanteRepository;
    @Autowired private ConsultorRepository consultorRepository;

    // -------- helpers (alineados a tus entidades reales) --------
    private Consultor nuevoConsultor(String nombre) {
        return consultorRepository.save(
                Consultor.builder()
                        .nombre(nombre)
                        .especializacion("Backend")
                        .inActivoActualizado(true)
                        .build()
        );
    }

    private Programa nuevoPrograma(String nombre) {
        // En tu modelo Programa NO tiene 'descripcion'; SÍ tiene estos campos:
        return Programa.builder()
                .nombre(nombre)
                .enfoque("basico")
                .duracion("40h")
                .institucion("UDB")
                .consultor(nuevoConsultor("Ada Lovelace")) // si es @ManyToOne(optional=false)
                .build();
    }

    private Participante nuevoParticipante(String nombre, String email) {
        return Participante.builder()
                .nombre(nombre)
                .email(email)
                .genero("F")
                .nivelEducativo("Licenciatura")
                .ingresoFormal(true)
                .build();
    }

    private Evaluacion nuevaEvaluacion(Programa programa, Participante participante, LocalDate fecha) {
        // En tu modelo Evaluacion usa 'puntaje' y 'observaciones' (no 'calificacion' ni 'comentarios')
        return Evaluacion.builder()
                .programa(programa)
                .participante(participante)
                .fecha(fecha)
                .puntaje(9.5)
                .observaciones("Muy bien")
                .build();
    }

    // -------- tests --------
    @Test
    @DisplayName("findByProgramaId devuelve evaluaciones ligadas a un programa")
    void findByProgramaId_returnsEvaluaciones() {
        Programa prog = programaRepository.save(nuevoPrograma("Java Avanzado"));
        Participante part = participanteRepository.save(nuevoParticipante("Ana", "ana@test.com"));
        evaluacionRepository.save(nuevaEvaluacion(prog, part, LocalDate.now()));

        List<Evaluacion> evaluaciones = evaluacionRepository.findByProgramaId(prog.getId());

        assertThat(evaluaciones).hasSize(1);
        assertThat(evaluaciones.get(0).getPrograma().getNombre()).isEqualTo("Java Avanzado");
    }

    @Test
    @DisplayName("findByParticipanteId devuelve evaluaciones ligadas a un participante")
    void findByParticipanteId_returnsEvaluaciones() {
        Programa prog = programaRepository.save(nuevoPrograma("Spring Boot"));
        Participante part = participanteRepository.save(nuevoParticipante("Luis", "luis@test.com"));
        evaluacionRepository.save(nuevaEvaluacion(prog, part, LocalDate.now()));

        List<Evaluacion> evaluaciones = evaluacionRepository.findByParticipanteId(part.getId());

        assertThat(evaluaciones).hasSize(1);
        assertThat(evaluaciones.get(0).getParticipante().getNombre()).isEqualTo("Luis");
    }

    @Test
    @DisplayName("existsByProgramaAndParticipanteAndFecha funciona correctamente")
    void existsByProgramaAndParticipanteAndFecha_returnsTrue() {
        Programa prog = programaRepository.save(nuevoPrograma("Angular"));
        Participante part = participanteRepository.save(nuevoParticipante("Maria", "maria@test.com"));
        LocalDate fecha = LocalDate.of(2025, 1, 1);

        evaluacionRepository.save(nuevaEvaluacion(prog, part, fecha));

        boolean exists = evaluacionRepository.existsByProgramaAndParticipanteAndFecha(prog, part, fecha);
        assertThat(exists).isTrue();

        boolean notExists = evaluacionRepository.existsByProgramaAndParticipanteAndFecha(prog, part, fecha.plusDays(1));
        assertThat(notExists).isFalse();
    }
}
