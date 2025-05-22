package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.*;
import com.oprosita.backend.repository.AlumnoRepository;
import com.oprosita.backend.repository.ContenidoItemRepository;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.service.ArchivoService;
import com.oprosita.backend.service.ContenidoItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContenidoItemServiceImpl implements ContenidoItemService {

    private final ContenidoItemRepository contenidoItemRepository;
    private final GrupoRepository grupoRepository;
    private final AlumnoRepository alumnoRepository;
    private final GeneralMapper mapper;
    private final ArchivoService archivoService;

    public ContenidoItemServiceImpl(ContenidoItemRepository contenidoItemRepository,
                                    GrupoRepository grupoRepository,
                                    AlumnoRepository alumnoRepository,
                                    GeneralMapper mapper,
                                    ArchivoService archivoService) {
        this.contenidoItemRepository = contenidoItemRepository;
        this.grupoRepository = grupoRepository;
        this.alumnoRepository = alumnoRepository;
        this.mapper = mapper;
        this.archivoService = archivoService;
    }

    @Override
    public ContenidoItemDto obtenerPorId(Long id) {
        ContenidoItem contenido = contenidoItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contenido no encontrado"));
        return mapper.toDto(contenido);
    }

    @Override
    public List<ContenidoItemDto> obtenerTodos() {
        return contenidoItemRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContenidoItemDto crear(ContenidoItemDto dto) {
        ContenidoItem contenido = mapper.toEntity(dto);
        if (dto.getGrupoId() != null) {
            Grupo grupo = grupoRepository.findById(dto.getGrupoId().longValue())
                    .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            contenido.setGrupo(grupo);
        }
        if (dto.getAlumnoId() != null) {
            Alumno alumno = alumnoRepository.findById(dto.getAlumnoId().longValue())
                    .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
            contenido.setAlumno(alumno);
        }
        contenido = contenidoItemRepository.save(contenido);
        return mapper.toDto(contenido);
    }

    @Override
    public ContenidoItemDto actualizar(Long id, ContenidoItemDto dto) {
        if (!contenidoItemRepository.existsById(id)) {
            throw new NotFoundException("Contenido no encontrado");
        }
        ContenidoItem contenido = mapper.toEntity(dto);
        contenido.setId(id);
        contenido = contenidoItemRepository.save(contenido);
        return mapper.toDto(contenido);
    }

    @Override
    public void eliminar(Long id) {
        if (!contenidoItemRepository.existsById(id)) {
            throw new NotFoundException("Contenido no encontrado");
        }
        contenidoItemRepository.deleteById(id);
    }

    @Override
    public List<ContenidoItemDto> obtenerPorAlumno(Long alumnoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
        return alumno.getContenidos().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContenidoItemDto crearParaAlumno(Long alumnoId, String texto, String tipoContenido, String mes, MultipartFile file) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));

        Long archivoId = archivoService.subirArchivo(file).getId().longValue();

        ContenidoItem contenido = ContenidoItem.builder()
                .texto(texto)
                .tipoContenido(TipoContenido.valueOf(tipoContenido))
                .mes(mes)
                .alumno(alumno)
                .archivoId(archivoId)
                .build();

        return mapper.toDto(contenidoItemRepository.save(contenido));
    }

    @Override
    public List<ContenidoItemDto> obtenerPorGrupoYMes(Long grupoId, String mes) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));

        return contenidoItemRepository.findAll().stream()
                .filter(c -> grupo.equals(c.getGrupo()) && mes.equalsIgnoreCase(c.getMes()))
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContenidoItemDto crearParaGrupoPorMes(Long grupoId, String mes, ContenidoItemDto contenidoDto) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));

        ContenidoItem contenido = mapper.toEntity(contenidoDto);
        contenido.setGrupo(grupo);
        contenido.setMes(mes);

        return mapper.toDto(contenidoItemRepository.save(contenido));
    }
}
