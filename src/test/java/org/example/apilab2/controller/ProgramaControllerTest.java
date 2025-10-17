package org.example.apilab2.controller;

import org.example.apilab2.service.ProgramaService;
import org.example.apilab2.service.dtos.ProgramaDto;
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

@WebMvcTest(ProgramaController.class)
class ProgramaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgramaService service;

    @Test
    void deberiaObtenerProgramaPorId() throws Exception {
        ProgramaDto dto = new ProgramaDto();
        dto.id = 1L;
        dto.nombre = "Programa de Alfabetización Digital";

        Mockito.when(service.obtener(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/programas/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Programa de Alfabetización Digital")));
    }

    @Test
    void deberiaCrearPrograma() throws Exception {
        ProgramaDto dto = new ProgramaDto();
        dto.nombre = "Nuevo Programa";

        Mockito.when(service.crear(Mockito.any())).thenReturn(dto);

        mockMvc.perform(post("/api/v1/programas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Nuevo Programa\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Nuevo Programa")));
    }
}
