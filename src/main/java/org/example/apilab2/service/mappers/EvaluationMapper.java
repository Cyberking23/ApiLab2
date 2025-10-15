package org.example.apilab2.service.mappers;

import org.example.apilab2.service.entities.Evaluation;
import org.example.apilab2.service.dtos.EvaluationDtos;
import org.mapstruct.*;

@Mapper(componentModel="spring")
public interface EvaluationMapper {
    @Mapping(target="id", ignore=true) @Mapping(target="participant", ignore=true)
    Evaluation toEntity(EvaluationDtos.Create dto);
    @Mapping(target="id", ignore=true) @Mapping(target="participant", ignore=true)
    void update(@MappingTarget Evaluation entity, EvaluationDtos.Update dto);
    @Mapping(target="participantId", source="participant.id")
    EvaluationDtos.Response toResponse(Evaluation entity);
}
