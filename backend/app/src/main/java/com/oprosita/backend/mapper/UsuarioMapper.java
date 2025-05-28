package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.model.generated.CrearUsuarioRequest;
import com.oprosita.backend.model.generated.Usuario;
import com.oprosita.backend.wrapper.UsuarioWrapperAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final AlumnoMapper alumnoMapper;
    private final ProfesorMapper profesorMapper;

    public Object mapToDto(CrearUsuarioRequest usuario) {
        String tipo = String.valueOf(usuario.getTipo());

        if ("alumno".equalsIgnoreCase(tipo)) {
            AlumnoDto alumnoDto = AlumnoDto.builder()
                    .id(usuario.getId())
                    .nombre(usuario.getNombre())
                    .grupoId(usuario.getGrupoId())
                    .build();
            return alumnoDto;

        } else if ("profesor".equalsIgnoreCase(tipo)) {
            ProfesorDto profesorDto = ProfesorDto.builder()
                    .id(usuario.getId())
                    .nombre(usuario.getNombre())
                    .grupoIds(usuario.getGrupoIds())
                    .build();
            return profesorDto;

        } else {
            throw new IllegalArgumentException("Tipo de CrearUsuarioRequest desconocido");
        }
    }


    public CrearUsuarioRequest mapToGenerated(Object dto) {
        if (dto instanceof AlumnoDto alumnoDto) {
            var alumno = alumnoMapper.toGeneratedAlumno(alumnoDto);
            return new CrearUsuarioRequest()
                    .id(alumno.getId())
                    .nombre(alumno.getNombre())
                    .grupoId(alumno.getGrupoId())
                    .tipo(CrearUsuarioRequest.TipoEnum.ALUMNO); // o alumno.getTipo()
        } else if (dto instanceof ProfesorDto profesorDto) {
            var profesor = profesorMapper.toGeneratedProfesor(profesorDto);
            return new CrearUsuarioRequest()
                    .id(profesor.getId())
                    .nombre(profesor.getNombre())
                    .grupoIds(profesor.getGrupoIds())
                    .tipo(CrearUsuarioRequest.TipoEnum.PROFESOR); // o profesor.getTipo()
        } else {
            throw new IllegalArgumentException("Tipo de DTO no soportado");
        }
    }


    public List<Usuario> mapToUsuarioList(List<Object> dtoList) {
        return dtoList.stream()
                .map(dto -> {
                    if (dto instanceof AlumnoDto alumnoDto) {
                        return alumnoMapper.toGeneratedAlumno(alumnoDto);
                    } else if (dto instanceof ProfesorDto profesorDto) {
                        return profesorMapper.toGeneratedProfesor(profesorDto);
                    } else {
                        throw new IllegalArgumentException("Tipo de DTO no soportado");
                    }
                })
                .map(obj -> {
                    return new UsuarioWrapperAdapter(obj);
                })
                .collect(Collectors.toList());
    }
}
