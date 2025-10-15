package org.example.apilab2.service.dtos;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public class EvaluationDtos {
    public record Create(@NotNull LocalDate date, @NotNull @DecimalMin("0.0") Double score, String notes, @NotNull Long participantId) { }
    public record Update(@NotNull LocalDate date, @NotNull @DecimalMin("0.0") Double score, String notes) { }
    public record Response(Long id, LocalDate date, Double score, String notes, Long participantId) { }
}
