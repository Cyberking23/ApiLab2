
package org.example.apilab2.service.mappers;

import org.mapstruct.*;
import org.example.apilab2.service.entities.Evaluacion;
import org.example.apilab2.service.dtos.EvaluacionDto;

@Mapper(componentModel = "spring")
public interface EvaluacionMapper {
    @Mapping(target = "participanteId", source = "participante.id")
    EvaluacionDto toDto(Evaluacion entity);

    @Mapping(target = "participante", ignore = true)
    Evaluacion toEntity(EvaluacionDto dto);
}
