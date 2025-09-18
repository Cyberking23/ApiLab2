package org.example.apilab2.repository;
import org.example.apilab2.repository.domain.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    Optional<Participante> findByEmail(String email);
}
