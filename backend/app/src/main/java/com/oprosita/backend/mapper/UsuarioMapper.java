package com.oprosita.backend.mapper;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.Profesor;
import com.oprosita.backend.model.generated.CrearUsuario201Response;
import com.oprosita.backend.model.generated.CrearUsuarioRequest;
import com.oprosita.backend.model.generated.Usuario;
import com.oprosita.backend.wrapper.UsuarioWrapperAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public CrearUsuario201Response mapToCrearUsuario201Response(Object dto) {
        if (dto instanceof AlumnoDto alumnoDto) {
            return new CrearUsuario201Response()
                    .id(alumnoDto.getId())
                    .nombre(alumnoDto.getNombre())
                    .grupoId(alumnoDto.getGrupoId())
                    .tipo(CrearUsuario201Response.TipoEnum.ALUMNO)
                    .grupoIds(alumnoDto.getGrupoId() != null
                            ? List.of(alumnoDto.getGrupoId())
                            : new ArrayList<>());
        } else if (dto instanceof ProfesorDto profesorDto) {
            return new CrearUsuario201Response()
                    .id(profesorDto.getId())
                    .nombre(profesorDto.getNombre())
                    .tipo(CrearUsuario201Response.TipoEnum.PROFESOR)
                    .grupoIds(profesorDto.getGrupoIds());
        } else {
            throw new IllegalArgumentException("Tipo de DTO no soportado");
        }
    }

    public Object mapToDto(CrearUsuario201Response usuario) {
        String tipo = String.valueOf(usuario.getTipo());

        if ("alumno".equalsIgnoreCase(tipo)) {
            return AlumnoDto.builder()
                    .id(usuario.getId())
                    .nombre(usuario.getNombre())
                    .grupoId(usuario.getGrupoId())
                    .build();
        } else if ("profesor".equalsIgnoreCase(tipo)) {
            return ProfesorDto.builder()
                    .id(usuario.getId())
                    .nombre(usuario.getNombre())
                    .grupoIds(usuario.getGrupoIds())
                    .build();
        } else {
            throw new IllegalArgumentException("Tipo de CrearUsuario201Response desconocido");
        }
    }

    public CrearUsuario201Response fromUsuario(com.oprosita.backend.model.Usuario usuario) {
        CrearUsuario201Response response = new CrearUsuario201Response()
                .id(Math.toIntExact(usuario.getId()))
                .nombre(usuario.getNombre());

        if (usuario instanceof Alumno alumno) {
            response.setTipo(CrearUsuario201Response.TipoEnum.valueOf("ALUMNO"));
            if (alumno.getGrupo() != null) {
                response.setGrupoId(Math.toIntExact(alumno.getGrupo().getId()));
            }
        } else if (usuario instanceof Profesor profesor) {
            response.setTipo(CrearUsuario201Response.TipoEnum.valueOf("PROFESOR"));
            List<Integer> grupoIds = profesor.getGrupos().stream()
                    .map(g -> Math.toIntExact(g.getId()))
                    .collect(Collectors.toList());
            response.setGrupoIds(grupoIds);
        }

        return response;
    }

}
