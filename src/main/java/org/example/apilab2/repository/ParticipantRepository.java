package org.example.apilab2.repository;
import org.example.apilab2.service.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ParticipantRepository extends JpaRepository<Participant,Long> {
    boolean existsByEmail(String email);
}
