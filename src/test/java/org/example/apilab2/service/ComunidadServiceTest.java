package org.example.apilab2.service;

import org.example.apilab2.repository.ComunidadRepository;
import org.example.apilab2.service.dtos.ComunidadDto;
import org.example.apilab2.service.entities.Comunidad;
import org.example.apilab2.service.impl.ComunidadServiceImpl;
import org.example.apilab2.service.mappers.ComunidadMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class ComunidadServiceTest {

    @Mock
    private ComunidadRepository repo;

    @Mock
    private ComunidadMapper mapper;

    @InjectMocks
    private ComunidadServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deberiaCrearComunidad() {
        ComunidadDto dto = new ComunidadDto();
        dto.nombre = "Comunidad A";

        Comunidad entidad = new Comunidad();
        entidad.setNombre("Comunidad A");

        when(mapper.toEntity(dto)).thenReturn(entidad);
        when(repo.save(entidad)).thenReturn(entidad);
        when(mapper.toDto(entidad)).thenReturn(dto);

        ComunidadDto resultado = service.crear(dto);

        assertThat(resultado.nombre).isEqualTo("Comunidad A");
        verify(repo, times(1)).save(entidad);
    }

    @Test
    void deberiaObtenerComunidadPorId() {
        Comunidad c = new Comunidad();
        c.setId(1L);
        c.setNombre("Comunidad Esperanza");

        when(repo.findById(1L)).thenReturn(java.util.Optional.of(c));

        ComunidadDto dto = new ComunidadDto();
        dto.id = 1L;
        dto.nombre = "Comunidad Esperanza";
        when(mapper.toDto(c)).thenReturn(dto);

        ComunidadDto resultado = service.obtener(1L);

        assertThat(resultado.nombre).isEqualTo("Comunidad Esperanza");
    }
}
