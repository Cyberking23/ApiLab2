package org.example.apilab2.controller;

import org.example.apilab2.service.ComunidadService;
import org.example.apilab2.service.dtos.ComunidadDto;
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

@WebMvcTest(ComunidadController.class)
class ComunidadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComunidadService service;

    @Test
    void deberiaObtenerComunidadPorId() throws Exception {
        ComunidadDto dto = new ComunidadDto();
        dto.id = 1L;
        dto.nombre = "Comunidad Esperanza";

        Mockito.when(service.obtener(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/comunidades/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Comunidad Esperanza")));
    }

    @Test
    void deberiaCrearComunidad() throws Exception {
        ComunidadDto dto = new ComunidadDto();
        dto.nombre = "Nueva Comunidad";

        Mockito.when(service.crear(Mockito.any())).thenReturn(dto);

        mockMvc.perform(post("/api/v1/comunidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Nueva Comunidad\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Nueva Comunidad")));
    }
}
