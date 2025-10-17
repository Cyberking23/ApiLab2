package org.example.apilab2.controller;

import org.example.apilab2.service.ParticipanteService;
import org.example.apilab2.service.dtos.ParticipanteDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParticipanteController.class)
class ParticipanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipanteService service;

    @Test
    void deberiaObtenerParticipantePorId() throws Exception {
        ParticipanteDto dto = new ParticipanteDto();
        dto.id = 1L;
        dto.nombre = "Juan Perez";

        Mockito.when(service.obtener(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/participantes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Juan Perez")));
    }

    @Test
    void deberiaCrearParticipante() throws Exception {
        ParticipanteDto dto = new ParticipanteDto();
        dto.nombre = "Ana Lopez";

        Mockito.when(service.crear(Mockito.any())).thenReturn(dto);

        mockMvc.perform(post("/api/v1/participantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Ana Lopez\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Ana Lopez")));
    }
}
