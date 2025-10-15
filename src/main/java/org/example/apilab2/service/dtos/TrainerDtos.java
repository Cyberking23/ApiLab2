package org.example.apilab2.service.dtos;
import jakarta.validation.constraints.*;
public class TrainerDtos {
    public record Create(@NotBlank String name, @NotNull @Min(0) Integer experienceYears, @NotBlank String academicLevel) { }
    public record Update(@NotBlank String name, @NotNull @Min(0) Integer experienceYears, @NotBlank String academicLevel) { }
    public record Response(Long id, String name, Integer experienceYears, String academicLevel) { }
}
