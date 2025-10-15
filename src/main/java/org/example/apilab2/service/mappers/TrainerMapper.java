package org.example.apilab2.service.mappers;

import org.example.apilab2.service.entities.Trainer;
import org.example.apilab2.service.dtos.TrainerDtos;
import org.mapstruct.*;

@Mapper(componentModel="spring")
public interface TrainerMapper {
    @Mapping(target="id", ignore=true) @Mapping(target="participants", ignore=true)
    Trainer toEntity(TrainerDtos.Create dto);
    @Mapping(target="id", ignore=true) @Mapping(target="participants", ignore=true)
    void update(@MappingTarget Trainer entity, TrainerDtos.Update dto);
    TrainerDtos.Response toResponse(Trainer entity);
}
