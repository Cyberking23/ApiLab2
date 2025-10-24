
package org.example.apilab2.service.mappers;

import org.mapstruct.*;
import org.example.apilab2.service.entities.Comunidad;
import org.example.apilab2.service.dtos.ComunidadDto;

@Mapper(componentModel = "spring")
public interface ComunidadMapper {
    ComunidadDto toDto(Comunidad entity);
    Comunidad toEntity(ComunidadDto dto);
}
