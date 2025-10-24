package org.example.apilab2.controller;

import org.example.apilab2.service.EvaluacionService;
import org.example.apilab2.service.dtos.EvaluacionDto;
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

@WebMvcTest(EvaluacionController.class)
class EvaluacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvaluacionService service;

    @Test
    void deberiaObtenerEvaluacionPorId() throws Exception {
        EvaluacionDto dto = new EvaluacionDto();
        dto.id = 1L;
        dto.observaciones = "Buen desempeño";

        Mockito.when(service.obtener(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/evaluaciones/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.observaciones", is("Buen desempeño")));
    }

    @Test
    void deberiaCrearEvaluacion() throws Exception {
        EvaluacionDto dto = new EvaluacionDto();
        dto.observaciones = "Excelente mejora";

        Mockito.when(service.crear(Mockito.any())).thenReturn(dto);

        mockMvc.perform(post("/api/v1/evaluaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"observaciones\":\"Excelente mejora\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.observaciones", is("Excelente mejora")));
    }
}
