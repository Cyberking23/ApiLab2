package org.example.apilab2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apilab2.repository.ConsultorRepository;
import org.example.apilab2.repository.domain.Consultor;
import org.example.apilab2.service.ConsultorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ConsultorController.class)
@Import(ConsultorControllerTest.TestConfig.class)
@ActiveProfiles("test")
class ConsultorControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        ConsultorService consultorService() { return Mockito.mock(ConsultorService.class); }
        @Bean
        ConsultorRepository consultorRepository() { return Mockito.mock(ConsultorRepository.class); }
    }

    @Autowired private MockMvc mvc;
    @Autowired private ConsultorService consultorService; // mock
    @Autowired private ObjectMapper mapper;

    @Test
    void crearConsultor_devuelve201YEntidad() throws Exception {
        // Mock del retorno del servicio
        Consultor mock = Consultor.builder()
                .id(10L)
                .nombre("Paola Campos")
                .especializacion("QA")
                .inActivoActualizado(false)
                .build();

        // Stubbing: firma crear(nombre, especializacion, boolean/Boolean)
        Mockito.when(consultorService.crear(
                        eq("Paola Campos"), eq("QA"), eq(false)))
                .thenReturn(mock);

        // CORRECCIÓN: el campo debe llamarse como en tu DTO: inActivoActualizado
        Map<String, Object> req = new HashMap<>();
        req.put("nombre", "Paola Campos");
        req.put("especializacion", "QA");
        req.put("inActivoActualizado", false); // <- antes estaba "inActivo"

        mvc.perform(post("/api/consultores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.nombre", is("Paola Campos")))
                .andExpect(jsonPath("$.especializacion", is("QA")))
                .andExpect(jsonPath("$.inActivoActualizado", is(false)));
    }

    @Test
    void crearConsultor_conDatosInvalidos_retorna400() throws Exception {
        Map<String, Object> req = new HashMap<>();
        req.put("nombre", ""); // inválido si @NotBlank en tu record
        req.put("especializacion", "QA");
        req.put("inActivoActualizado", true);

        mvc.perform(post("/api/consultores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
}
