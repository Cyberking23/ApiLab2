package org.example.apilab2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apilab2.repository.EvaluacionRepository;
import org.example.apilab2.repository.domain.Evaluacion;
import org.example.apilab2.service.EvaluacionService;
import org.example.apilab2.service.ParticipanteService;
import org.example.apilab2.service.ProgramaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EvaluacionController.class)
@Import(EvaluacionControllerTest.MockConfig.class)
class EvaluacionControllerTest {

    // ⚠️ Si tu POST no está en /api/evaluaciones/crear, cambia esta constante a tu ruta real.
    private static final String CREATE_PATH = "/api/evaluaciones/crear";

    @TestConfiguration
    static class MockConfig {
        @Bean EvaluacionService evaluacionService() { return Mockito.mock(EvaluacionService.class); }
        @Bean ProgramaService programaService() { return Mockito.mock(ProgramaService.class); }
        @Bean ParticipanteService participanteService() { return Mockito.mock(ParticipanteService.class); }
        @Bean EvaluacionRepository evaluacionRepository() { return Mockito.mock(EvaluacionRepository.class); }
    }

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper om;
    @Autowired private EvaluacionService evaluacionService;

    @Test
    @DisplayName("POST crear evaluación -> 201 Created")
    void crearEvaluacion_ok_201() throws Exception {
        String json = """
        {
          "fecha": "2025-09-19",
          "puntaje": 0.8,
          "observaciones": "Buen avance",
          "participanteId": 5,
          "programaId": 12
        }
        """;

        // Entidad simulada que devolverá el controller
        Evaluacion ev = Mockito.mock(Evaluacion.class);
        Mockito.when(ev.getId()).thenReturn(100L);
        Mockito.when(ev.getPuntaje()).thenReturn(0.8);

        // ✅ Firma real: (Long, Long, LocalDate, Double, String)
        Mockito.when(evaluacionService.crear(
                anyLong(),                    // participanteId
                anyLong(),                    // programaId
                any(LocalDate.class),         // fecha
                anyDouble(),                  // puntaje
                anyString()                   // observaciones
        )).thenReturn(ev);

        mockMvc.perform(post(CREATE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.puntaje").value(0.8));
    }

    @Test
    @DisplayName("GET /api/evaluaciones/impacto/{programaId} -> 200 OK")
    void impactoPrograma_ok_200() throws Exception {
        Long programaId = 12L;

        // Si tu controller calcula/retorna el impacto directamente, no necesitas stub.
        // Si usas servicio y el método se llama distinto, puedes stubbearlo aquí cuando lo sepas.

        mockMvc.perform(get("/api/evaluaciones/impacto/{programaId}", programaId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("0.85")));
    }
}
