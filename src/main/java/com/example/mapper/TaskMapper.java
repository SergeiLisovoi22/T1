package com.example.mapper;

import com.example.domain.Task;
import com.example.web.controller.model.RequestTaskDTO;
import com.example.web.controller.model.ResponseTaskDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(RequestTaskDTO dto);

    ResponseTaskDTO toDTO(Task entity);

    List<ResponseTaskDTO> toListDTO(List<Task> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task updateEntityFromRequest(Task individual, @MappingTarget Task individualFromDb);
}
