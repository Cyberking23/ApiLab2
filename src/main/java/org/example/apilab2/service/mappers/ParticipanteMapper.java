
package org.example.apilab2.service.mappers;

import org.mapstruct.*;
import org.example.apilab2.service.entities.Participante;
import org.example.apilab2.service.dtos.ParticipanteDto;

@Mapper(componentModel = "spring")
public interface ParticipanteMapper {
    @Mapping(target = "programaId", source = "programa.id")
        // comunidadesIds se rellena en el servicio
    ParticipanteDto toDto(Participante entity);

    @Mapping(target = "programa", ignore = true)
    @Mapping(target = "comunidades", ignore = true)
    @Mapping(target = "evaluaciones", ignore = true)
    Participante toEntity(ParticipanteDto dto);
}
