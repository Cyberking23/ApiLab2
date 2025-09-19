package org.example.apilab2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apilab2.repository.ParticipanteRepository;
import org.example.apilab2.repository.domain.Participante;
import org.example.apilab2.service.ParticipanteService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ParticipanteController.class)
@Import(ParticipanteControllerTest.MockConfig.class)
class ParticipanteControllerTest {

    @TestConfiguration
    static class MockConfig {
        @Bean ParticipanteService participanteService() { return Mockito.mock(ParticipanteService.class); }
        @Bean ParticipanteRepository participanteRepository() { return Mockito.mock(ParticipanteRepository.class); }
    }

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper om;
    @Autowired private ParticipanteService participanteService;

    @Test
    @DisplayName("POST /api/participantes -> 201 Created")
    void crearParticipante_ok_201() throws Exception {
        String json = """
        {
          "nombre": "Ana Pérez",
          "email": "ana.perez@example.com",
          "genero": "F",
          "nivelEducativo": "Universitario",
          "ingresoFormal": true
        }
        """;

        // mock de entidad que devuelve el controller
        Participante p = Mockito.mock(Participante.class);
        Mockito.when(p.getId()).thenReturn(7L);
        Mockito.when(p.getNombre()).thenReturn("Ana Pérez");
        Mockito.when(p.getEmail()).thenReturn("ana.perez@example.com");

        // firma laxa: acepta cualquier tipo de argumentos
        Mockito.when(participanteService.crear(any(), any(), any(), any(), any()))
                .thenReturn(p);

        mockMvc.perform(post("/api/participantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.nombre").value("Ana Pérez"))
                .andExpect(jsonPath("$.email").value("ana.perez@example.com"));
    }

    @Test
    @DisplayName("POST /api/participantes inválido (email) -> 400")
    void crearParticipante_emailInvalido_400() throws Exception {
        String badJson = """
        {
          "nombre": "Ana",
          "email": "correo-malo",
          "genero": "F",
          "nivelEducativo": "Universitario",
          "ingresoFormal": true
        }
        """;

        mockMvc.perform(post("/api/participantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badJson))
                .andExpect(status().isBadRequest());
    }
}
