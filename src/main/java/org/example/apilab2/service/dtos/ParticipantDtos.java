package org.example.apilab2.service.dtos;

import org.example.apilab2.service.entities.Gender;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ParticipantDtos {
    public record Create(@NotBlank String name, @NotNull @Min(12) @Max(99) Integer age, @NotNull Gender gender,
                         @NotBlank String educationLevel, @NotNull @DecimalMin("0.0") BigDecimal familyIncome,
                         @NotBlank @Email String email, @NotNull Long trainerId) { }
    public record Update(@NotBlank String name, @NotNull @Min(12) @Max(99) Integer age, @NotNull Gender gender,
                         @NotBlank String educationLevel, @NotNull @DecimalMin("0.0") BigDecimal familyIncome) { }
    public record Response(Long id, String name, Integer age, Gender gender, String educationLevel,
                           BigDecimal familyIncome, String email, Trainer trainer, List<Evaluation> evaluations) {
        public record Trainer(Long id, String name, Integer experienceYears, String academicLevel) { }
        public record Evaluation(Long id, LocalDate date, Double score, String notes) { }
    }
}
