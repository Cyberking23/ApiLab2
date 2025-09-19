package org.example.apilab2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.apilab2.repository.ProgramaRepository;
import org.example.apilab2.repository.domain.Consultor;
import org.example.apilab2.repository.domain.Programa;
import org.example.apilab2.service.EvaluacionService;
import org.example.apilab2.service.ProgramaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProgramaController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        ProgramaControllerTest.MockBeansConfig.class, // <- inyecta mocks como beans
        ProgramaController.class
})
class ProgramaControllerTest {

    private static final String BASE_PATH = "/api/programas"; // ajusta si tu @RequestMapping cambia

    @TestConfiguration
    static class MockBeansConfig {
        @Bean ProgramaService programaService() { return Mockito.mock(ProgramaService.class); }
        @Bean ProgramaRepository programaRepository() { return Mockito.mock(ProgramaRepository.class); }
        @Bean EvaluacionService evaluacionService() { return Mockito.mock(EvaluacionService.class); }
    }

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;

    // Autowire de los mocks para poder stubbearlos con Mockito
    @Autowired private ProgramaService programaService;

    @Test
    @DisplayName("POST /api/programas crea y devuelve consultor.id")
    void shouldCreateProgramaAndReturnConsultorId() throws Exception {
        // Arrange
        Long consultorId = 10L;

        Consultor consultor = Mockito.mock(Consultor.class);
        Mockito.when(consultor.getId()).thenReturn(consultorId);

        Programa programa = Mockito.mock(Programa.class);
        Mockito.when(programa.getConsultor()).thenReturn(consultor);
        Mockito.when(programa.getId()).thenReturn(1L);

        // firma: crear(String, String, String, String, Long)
        Mockito.when(programaService.crear(
                anyString(), anyString(), anyString(), anyString(), anyLong()
        )).thenReturn(programa);

        // Act + Assert (versión form-urlencoded; usa JSON si tu controller recibe @RequestBody)
        mvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("""
           {
             "nombre": "Programa Alfa",
             "descripcion": "Desc",
             "categoria": "CAT1",
             "empresa": "Empresa X",
             "consultorId": 10
           }
        """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.consultor.id").value(10));

    }
}
