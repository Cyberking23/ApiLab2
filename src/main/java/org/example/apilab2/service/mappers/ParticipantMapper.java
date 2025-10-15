package org.example.apilab2.service.mappers;

import org.example.apilab2.service.entities.*;
import org.example.apilab2.service.dtos.ParticipantDtos;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel="spring")
public interface ParticipantMapper {
    @Mapping(target="id", ignore=true) @Mapping(target="trainer", ignore=true) @Mapping(target="evaluations", ignore=true)
    Participant toEntity(ParticipantDtos.Create dto);
    @Mapping(target="id", ignore=true) @Mapping(target="email", ignore=true) @Mapping(target="trainer", ignore=true) @Mapping(target="evaluations", ignore=true)
    void update(@MappingTarget Participant entity, ParticipantDtos.Update dto);
    @Mapping(target="trainer", expression="java(mapTrainer(entity.getTrainer()))")
    @Mapping(target="evaluations", expression="java(mapEvaluations(entity.getEvaluations()))")
    ParticipantDtos.Response toResponse(Participant entity);
    List<ParticipantDtos.Response> toResponse(List<Participant> list);
    default ParticipantDtos.Response.Trainer mapTrainer(Trainer t){
        if(t==null) return null;
        return new ParticipantDtos.Response.Trainer(t.getId(), t.getName(), t.getExperienceYears(), t.getAcademicLevel());
    }
    default List<ParticipantDtos.Response.Evaluation> mapEvaluations(List<Evaluation> list){
        return list==null? List.of() : list.stream().map(e -> new ParticipantDtos.Response.Evaluation(e.getId(), e.getDate(), e.getScore(), e.getNotes())).toList();
    }
}
