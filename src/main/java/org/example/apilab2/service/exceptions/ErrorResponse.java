package org.example.apilab2.service.exceptions;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(Instant timestamp, String path, String code, String message, List<String> details) {
    public static ErrorResponse of(String code, String message, String path) {
        return new ErrorResponse(Instant.now(), path, code, message, List.of());
    }
    public static ErrorResponse of(String code, String message, String path, List<String> details) {
        return new ErrorResponse(Instant.now(), path, code, message, details);
    }
}
