package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.model.Profesor;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.util.MapperUtil;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = MapperUtil.class)
public interface ProfesorMapper {

    // JPA ↔ DTO
    @Mapping(source = "grupos", target = "grupoIds")
    ProfesorDto toProfesorDto(Profesor profesor);

    @Mapping(source = "grupoIds", target = "grupos")
    Profesor toProfesorEntity(ProfesorDto profesorDto);

    List<ProfesorDto> toProfesorDtoList(List<Profesor> profesores);
    List<Profesor> toProfesorEntityList(List<ProfesorDto> profesoresDto);

    // OpenAPI ↔ DTO
    ProfesorDto fromGeneratedProfesor(com.oprosita.backend.model.generated.Profesor generated);
    com.oprosita.backend.model.generated.Profesor toGeneratedProfesor(ProfesorDto dto);

    // Métodos auxiliares para mapear grupoIds ↔ grupos
    default List<Integer> mapGruposToIds(List<Grupo> grupos) {
        return grupos != null
                ? grupos.stream()
                .map(grupo -> grupo.getId() != null ? grupo.getId().intValue() : null)
                .collect(Collectors.toList())
                : null;
    }

    default List<Grupo> mapIdsToGrupos(List<Integer> ids) {
        return ids != null
                ? ids.stream().map(id -> {
            Grupo grupo = new Grupo();
            grupo.setId(id != null ? id.longValue() : null);
            return grupo;
        }).collect(Collectors.toList())
                : null;
    }
}
