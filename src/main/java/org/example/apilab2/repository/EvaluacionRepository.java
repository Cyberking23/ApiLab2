package org.example.apilab2.repository;
import org.example.apilab2.repository.domain.Evaluacion;
import org.example.apilab2.repository.domain.Participante;
import org.example.apilab2.repository.domain.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findByProgramaId(Long programaId);
    List<Evaluacion> findByParticipanteId(Long participanteId);
    boolean existsByProgramaAndParticipanteAndFecha(Programa p, Participante a, LocalDate f);
}
