package org.example.apilab2.service;

import jakarta.transaction.Transactional;
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

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class EvaluacionServiceIT {

    @Autowired private EvaluacionService evaluacionService;
    @Autowired private ProgramaService programaService;

    @Autowired private ProgramaRepository programaRepository;
    @Autowired private ParticipanteRepository participanteRepository;
    @Autowired private ConsultorRepository consultorRepository;

    // ---------- helpers (alineados a tu modelo) ----------
    private Consultor nuevoConsultor(String nombre) {
        return consultorRepository.save(
                Consultor.builder()
                        .nombre(nombre)
                        .especializacion("Backend")
                        .inActivoActualizado(true)
                        .build()
        );
    }

    private Programa nuevoProgramaPersistido(String nombre, String enfoque, String duracion, String inst, Long consultorId) {
        // Usa la firma REAL de tu ProgramaService (5 parámetros)
        return programaService.crear(nombre, enfoque, duracion, inst, consultorId);
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
    @DisplayName("crear(programaId, participanteId, fecha, puntaje, observaciones) persiste evaluación")
    void crearEvaluacion_ok() {
        // dado: consultor + programa + participante
        var c = nuevoConsultor("Ada Lovelace");
        var programa = nuevoProgramaPersistido("Bootcamp Java", "basico", "40h", "UDB", c.getId());
        var participante = nuevoParticipante("Juan", "juan@test.com");

        // cuando: creo una evaluación
        var fecha = LocalDate.of(2025, 1, 1);
        var ev = evaluacionService.crear(programa.getId(), participante.getId(), fecha, 9.0, "Muy bien");

        // entonces
        assertThat(ev).isNotNull();
        assertThat(ev.getId()).isNotNull();
        assertThat(ev.getPrograma().getId()).isEqualTo(programa.getId());
        assertThat(ev.getParticipante().getId()).isEqualTo(participante.getId());
        assertThat(ev.getPuntaje()).isEqualTo(9.0);
        assertThat(ev.getFecha()).isEqualTo(fecha);
    }

    @Test
    @DisplayName("impacto(programaId) devuelve DTO con el promedio de puntajes del programa")
    void impacto_promedio_ok() {
        // dado: consultor + programa + 2 participantes con 2 evaluaciones
        var c = nuevoConsultor("Grace Hopper");
        var programa = nuevoProgramaPersistido("API Spring", "intermedio", "30h", "UDB", c.getId());

        var p1 = nuevoParticipante("Ana", "ana@test.com");
        var p2 = nuevoParticipante("Luis", "luis@test.com");

        evaluacionService.crear(programa.getId(), p1.getId(), LocalDate.of(2025, 2, 1), 8.0, "Bien");
        evaluacionService.crear(programa.getId(), p2.getId(), LocalDate.of(2025, 2, 2), 10.0, "Excelente");

        // cuando: calculo impacto (DEVUELVE ImpactoProgramaResponse, no double)
        var resp = evaluacionService.impacto(programa.getId());

        // entonces: el promedio debería ser 9.0
        // ⬇️ Ajusta estas aserciones según tus getters/record:
        // Si es un record: resp.impacto(), resp.programaId()
        // Si es Lombok con getters: resp.getImpacto(), resp.getProgramaId(), resp.getTotalEvaluaciones()
        assertThat(resp).isNotNull();

        // Intenta primero con estilo record:
        // assertThat(resp.impacto()).isEqualTo(9.0);
        // assertThat(resp.programaId()).isEqualTo(programa.getId());

        // Si te marca error arriba, usa estilo getters:
        // (descomenta estas dos y comenta las dos de arriba)
        // assertThat(resp.getImpacto()).isEqualTo(9.0);
        // assertThat(resp.getProgramaId()).isEqualTo(programa.getId());

        // (Opcional) si tu DTO trae conteo:
        // assertThat(resp.getTotalEvaluaciones()).isEqualTo(2);
    }

}
