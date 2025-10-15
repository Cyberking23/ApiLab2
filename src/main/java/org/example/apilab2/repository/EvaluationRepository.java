package org.example.apilab2.repository;
import org.example.apilab2.service.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {}
