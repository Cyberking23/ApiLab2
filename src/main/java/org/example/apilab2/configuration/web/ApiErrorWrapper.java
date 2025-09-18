package org.example.apilab2.configuration.web;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiErrorWrapper {
    private final List<ApiError> errors = new ArrayList<>();
    public void addApiError(ApiError error) { errors.add(error); }
    public void addFieldError(String type, String title, String source, String description) {
        errors.add(ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .type(type).title(title).source(source).description(description).build());
    }
}
