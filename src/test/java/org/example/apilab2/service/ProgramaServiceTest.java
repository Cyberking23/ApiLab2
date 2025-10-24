package org.example.apilab2.service;

import org.example.apilab2.repository.ProgramaRepository;
import org.example.apilab2.service.dtos.ProgramaDto;
import org.example.apilab2.service.entities.Programa;
import org.example.apilab2.service.impl.ProgramaServiceImpl;
import org.example.apilab2.service.mappers.ProgramaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class ProgramaServiceTest {

    @Mock
    private ProgramaRepository repo;

    @Mock
    private ProgramaMapper mapper;

    @InjectMocks
    private ProgramaServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deberiaCrearPrograma() {
        ProgramaDto dto = new ProgramaDto();
        dto.nombre = "Programa A";

        Programa entity = new Programa();
        entity.setNombre("Programa A");

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ProgramaDto resultado = service.crear(dto);

        assertThat(resultado.nombre).isEqualTo("Programa A");
        verify(repo, times(1)).save(entity);
    }

    @Test
    void deberiaObtenerProgramaPorId() {
        Programa p = new Programa();
        p.setId(1L);
        p.setNombre("Programa Prueba");

        when(repo.findById(1L)).thenReturn(java.util.Optional.of(p));

        ProgramaDto dto = new ProgramaDto();
        dto.id = 1L;
        dto.nombre = "Programa Prueba";
        when(mapper.toDto(p)).thenReturn(dto);

        ProgramaDto resultado = service.obtener(1L);

        assertThat(resultado.id).isEqualTo(1L);
        assertThat(resultado.nombre).isEqualTo("Programa Prueba");
    }
}
