
package org.example.apilab2.service.mappers;

import org.mapstruct.*;
import org.example.apilab2.service.entities.Programa;
import org.example.apilab2.service.dtos.ProgramaDto;

@Mapper(componentModel = "spring")
public interface ProgramaMapper {
    ProgramaDto toDto(Programa entity);
    Programa toEntity(ProgramaDto dto);
}
