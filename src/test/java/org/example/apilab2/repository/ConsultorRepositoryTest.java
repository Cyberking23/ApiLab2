package org.example.apilab2.repository;

import org.example.apilab2.repository.domain.Consultor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
// Asegura que el contexto vea entidades y repositorios
@EntityScan(basePackages = "org.example.apilab2.repository.domain")
@EnableJpaRepositories(basePackages = "org.example.apilab2.repository")
// Usa la URL de H2 que definimos en application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConsultorRepositoryTest {

    @Autowired
    private ConsultorRepository consultorRepository;

    @BeforeEach
    void beforeEach() {
        // Limpieza defensiva; si hay FKs, limpia primero hijos (no aplica aquí)
        consultorRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        consultorRepository.deleteAll();
    }

    @Test
    void guardarYRecuperarConsultor() {
        // Arrange
        Consultor c = Consultor.builder()
                .nombre("Ana Torres")
                .especializacion("Agile Coach")
                .inActivoActualizado(false) // usa el campo REAL de tu entidad
                .build();

        // Act
        Consultor saved = consultorRepository.save(c);

        // Assert
        assertNotNull(saved.getId(), "El ID debe ser generado");

        Consultor found = consultorRepository.findById(saved.getId()).orElseThrow();
        assertEquals("Ana Torres", found.getNombre());
        assertEquals("Agile Coach", found.getEspecializacion());
        // Para Boolean wrapper el getter es getXxx()
        assertEquals(Boolean.FALSE, found.getInActivoActualizado());
    }

    @Test
    void verificarPersistenciaEnBD_conFindAll_baselineSeguro() {
        // No asumimos que la BD está vacía; medimos baseline primero
        long baseline = consultorRepository.count();

        consultorRepository.save(Consultor.builder()
                .nombre("Juan Perez")
                .especializacion("DevOps")
                .inActivoActualizado(true)
                .build());

        consultorRepository.save(Consultor.builder()
                .nombre("Maria Lopez")
                .especializacion("Data")
                .inActivoActualizado(false)
                .build());

        List<Consultor> all = consultorRepository.findAll();

        // Esperamos +2 respecto al baseline (sea cual sea)
        assertEquals(baseline + 2, all.size(), "El total debe aumentar en 2");

        assertTrue(all.stream().anyMatch(c ->
                "Juan Perez".equals(c.getNombre()) && Boolean.TRUE.equals(c.getInActivoActualizado())));
        assertTrue(all.stream().anyMatch(c ->
                "Maria Lopez".equals(c.getNombre()) && Boolean.FALSE.equals(c.getInActivoActualizado())));
    }
}
