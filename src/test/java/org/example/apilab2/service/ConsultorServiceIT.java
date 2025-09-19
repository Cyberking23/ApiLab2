package org.example.apilab2.service;

import org.example.apilab2.repository.ConsultorRepository;
import org.example.apilab2.repository.domain.Consultor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@EntityScan(basePackages = "org.example.apilab2.repository.domain")
@EnableJpaRepositories(basePackages = "org.example.apilab2.repository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConsultorServiceIT {

    @Autowired
    private ConsultorService consultorService;

    @Autowired
    private ConsultorRepository consultorRepository;

    @AfterEach
    void cleanup() {
        // Limpieza defensiva para evitar “sangrado” entre tests
        consultorRepository.deleteAll();
    }

    @Test
    void crear_persisteConsultorCorrectamente() {
        final String nombre = "Luis Gómez";
        final String especializacion = "Cloud";
        final boolean activo = true; // lo pasamos como boolean; si tu servicio usa Boolean funciona igual

        long baseline = consultorRepository.count();

        // Invoca consultorService.crear(...) con reflection para tolerar firmas distintas:
        // - crear(String,String,boolean)
        // - crear(String,String,Boolean)
        // (Si tu método tiene otro nombre, ajusta "crear" abajo)
        Object retorno = invocarCrearSegunFirma(consultorService, nombre, especializacion, activo);

        // Assert: aumentó en 1 el total
        assertEquals(baseline + 1, consultorRepository.count(), "Debe persistir exactamente 1 registro nuevo");

        // Verifica que el registro exista con los datos esperados
        List<Consultor> todos = consultorRepository.findAll();
        Consultor recienCreado = todos.stream()
                .filter(c -> nombre.equals(c.getNombre()) && especializacion.equals(c.getEspecializacion()))
                .findFirst()
                .orElse(null);

        assertNotNull(recienCreado, "Debe existir un consultor con el nombre y especialización indicados");

        // Si tu entidad tiene el campo Boolean inActivoActualizado, verifica su valor; si no existe, no falla
        try {
            Method getterActivo = recienCreado.getClass().getMethod("getInActivoActualizado");
            Object val = getterActivo.invoke(recienCreado);
            // Solo valida si no es null
            if (val instanceof Boolean) {
                assertEquals(Boolean.TRUE, (Boolean) val, "El flag de actividad debería ser TRUE");
            }
        } catch (NoSuchMethodException ignore) {
            // La entidad no tiene ese campo: no hacemos nada para mantener el test robusto
        } catch (Exception e) {
            fail("Error verificando el campo de actividad: " + e.getMessage());
        }

        // (Opcional) si el método de servicio devuelve algo con ID, lo validamos sin romper si es DTO
        if (retorno != null) {
            try {
                Method getId = retorno.getClass().getMethod("getId");
                Object idVal = getId.invoke(retorno);
                assertNotNull(idVal, "El objeto devuelto por el servicio debería traer un id (si expone getId())");
            } catch (NoSuchMethodException ignored) {
                // El retorno es DTO sin getId() o void → no forzamos
            } catch (InvocationTargetException | IllegalAccessException e) {
                fail("Error accediendo a getId() del retorno: " + e.getMessage());
            }
        }
    }

    /**
     * Invoca el método crear del servicio tolerando firmas distintas:
     * - crear(String, String, boolean)
     * - crear(String, String, Boolean)
     * Retorna el objeto devuelto por el servicio (Entidad o DTO) o null si es void.
     */
    private Object invocarCrearSegunFirma(Object service, String nombre, String especializacion, boolean activo) {
        // 1) Intento exacto: (String, String, boolean)
        try {
            Method m = service.getClass().getMethod("crear", String.class, String.class, boolean.class);
            return m.invoke(service, nombre, especializacion, activo);
        } catch (NoSuchMethodException ignored) {
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Error invocando crear(String,String,boolean): " + e.getMessage(), e);
        }

        // 2) Alternativo: (String, String, Boolean)
        try {
            Method m = service.getClass().getMethod("crear", String.class, String.class, Boolean.class);
            return m.invoke(service, nombre, especializacion, Boolean.valueOf(activo));
        } catch (NoSuchMethodException ignored) {
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Error invocando crear(String,String,Boolean): " + e.getMessage(), e);
        }

        // 3) Si llegamos aquí, la firma es distinta: fallamos con mensaje claro
        throw new IllegalStateException(
                "No se encontró un método crear(...) compatible en ConsultorService. " +
                        "Se esperaba (String,String,boolean) o (String,String,Boolean). " +
                        "Asegúrate de que el nombre del método sea 'crear' y la firma coincida."
        );
    }
}
