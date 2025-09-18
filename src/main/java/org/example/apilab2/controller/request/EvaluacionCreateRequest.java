package org.example.apilab2.controller.request;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EvaluacionCreateRequest(
        @NotNull Long participanteId,
        @NotNull @PastOrPresent LocalDate fecha,
        @NotNull @Min(0) @Max(100) Double puntaje,
        String observaciones
) {}
