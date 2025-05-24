package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.AlumnoMapper;
import com.oprosita.backend.mapper.ContenidoItemMapper;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.ContenidoItem;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.TipoContenido;
import com.oprosita.backend.repository.AlumnoRepository;
import com.oprosita.backend.repository.ContenidoItemRepository;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.service.AlumnoService;
import com.oprosita.backend.service.ArchivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;
    private final ContenidoItemRepository contenidoItemRepository;
    private final AlumnoMapper alumnoMapper;
    private final GrupoMapper grupoMapper;
    private final ContenidoItemMapper contenidoItemMapper;
    private final ArchivoService archivoService;

    @Override
    public AlumnoDto obtenerPorId(Long id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
        return alumnoMapper.toAlumnoDto(alumno);
    }

    @Override
    public List<AlumnoDto> obtenerTodos() {
        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::toAlumnoDto)
                .collect(Collectors.toList());
    }

    @Override
    public AlumnoDto crear(AlumnoDto dto) {
        Alumno alumno = alumnoMapper.toAlumnoEntity(dto);
        return alumnoMapper.toAlumnoDto(alumnoRepository.save(alumno));
    }

    @Override
    public AlumnoDto actualizar(Long id, AlumnoDto dto) {
        if (!alumnoRepository.existsById(id)) {
            throw new NotFoundException("Alumno no encontrado");
        }
        Alumno alumno = alumnoMapper.toAlumnoEntity(dto);
        alumno.setId(id);
        return alumnoMapper.toAlumnoDto(alumnoRepository.save(alumno));
    }

    @Override
    public void eliminar(Long id) {
        if (!alumnoRepository.existsById(id)) {
            throw new NotFoundException("Alumno no encontrado");
        }
        alumnoRepository.deleteById(id);
    }

    @Override
    public List<AlumnoDto> obtenerAlumnosPorGrupo(Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        return grupo.getUsuarios().stream()
                .filter(Alumno.class::isInstance)
                .map(usuario -> alumnoMapper.toAlumnoDto((Alumno) usuario))
                .collect(Collectors.toList());
    }

    @Override
    public AlumnoDto agregarAlumnoAGrupo(Long grupoId, AlumnoDto alumnoDto) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        Alumno alumno = alumnoMapper.toAlumnoEntity(alumnoDto);
        alumno.setGrupo(grupo);
        return alumnoMapper.toAlumnoDto(alumnoRepository.save(alumno));
    }

    @Override
    public void eliminarAlumnoDeGrupo(Long grupoId, Long alumnoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
        if (alumno.getGrupo() != null && alumno.getGrupo().getId().equals(grupoId)) {
            alumno.setGrupo(null);
            alumnoRepository.save(alumno);
        }
    }

    @Override
    public List<ContenidoItemDto> obtenerContenidoPorAlumno(Long alumnoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
        return alumno.getContenidos().stream()
                .map(contenidoItemMapper::toContenidoItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContenidoItemDto agregarContenidoAAlumno(Long alumnoId, String texto, String tipoContenido, String mes, MultipartFile file) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));

        ArchivoDto archivoDto = archivoService.subirArchivo(file);
        Long archivoId = archivoDto.getId().longValue();

        ContenidoItem contenido = ContenidoItem.builder()
                .texto(texto)
                .tipoContenido(TipoContenido.valueOf(tipoContenido))
                .mes(mes)
                .alumno(alumno)
                .archivoId(archivoId)
                .build();

        contenido = contenidoItemRepository.save(contenido);
        return contenidoItemMapper.toContenidoItemDto(contenido);
    }

    @Override
    public void eliminarContenidoDeAlumno(Long alumnoId, Long contenidoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));

        ContenidoItem contenido = contenidoItemRepository.findById(contenidoId)
                .orElseThrow(() -> new NotFoundException("Contenido no encontrado"));

        if (contenido.getAlumno() != null && contenido.getAlumno().getId().equals(alumnoId)) {
            contenidoItemRepository.deleteById(contenidoId);
        } else {
            throw new NotFoundException("Contenido no pertenece al alumno");
        }
    }

    @Override
    public List<GrupoDto> obtenerGrupoPorAlumno(Long alumnoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));

        if (alumno.getGrupo() == null) {
            return List.of();
        }

        return List.of(grupoMapper.toGrupoDto(alumno.getGrupo()));
    }
}
