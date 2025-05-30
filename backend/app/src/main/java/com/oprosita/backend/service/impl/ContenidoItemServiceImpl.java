package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.ArchivoMapper;
import com.oprosita.backend.mapper.ContenidoItemMapper;
import com.oprosita.backend.model.*;
import com.oprosita.backend.repository.*;
import com.oprosita.backend.service.ArchivoService;
import com.oprosita.backend.service.ContenidoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ContenidoItemServiceImpl implements ContenidoItemService {

    private final ContenidoItemRepository contenidoItemRepository;
    private final GrupoRepository grupoRepository;
    private final AlumnoRepository alumnoRepository;
    private final ArchivoRepository archivoRepository;
    private final MesRepository mesRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContenidoItemMapper contenidoItemMapper;
    private final ArchivoMapper archivoMapper;
    private final ArchivoService archivoService;

    @Override
    public ContenidoItemDto obtenerPorId(Long id) {
        ContenidoItem contenido = contenidoItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contenido no encontrado"));
        return contenidoItemMapper.toContenidoItemDto(contenido);
    }

    @Override
    public List<ContenidoItemDto> obtenerTodos() {
        return contenidoItemRepository.findAll().stream()
                .map(contenidoItemMapper::toContenidoItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContenidoItemDto crear(ContenidoItemDto dto) {
        ContenidoItem contenido = contenidoItemMapper.toContenidoItemEntity(dto);

        if (dto.getMesId() != null) {
            Mes mes = mesRepository.findById(dto.getMesId().longValue())
                    .orElseThrow(() -> new NotFoundException("Mes no encontrado"));
            contenido.setMes(mes);
        }

        if (dto.getAutorId() != null) {
            Alumno alumno = alumnoRepository.findById(dto.getAutorId().longValue())
                    .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
            contenido.setAutor(alumno);
        }

        contenido = contenidoItemRepository.save(contenido);
        return contenidoItemMapper.toContenidoItemDto(contenido);
    }

    @Override
    public ContenidoItemDto actualizar(Long id, ContenidoItemDto dto) {
        if (!contenidoItemRepository.existsById(id)) {
            throw new NotFoundException("Contenido no encontrado");
        }
        ContenidoItem contenido = contenidoItemMapper.toContenidoItemEntity(dto);
        contenido.setId(id);
        contenido = contenidoItemRepository.save(contenido);
        return contenidoItemMapper.toContenidoItemDto(contenido);
    }

    @Override
    public void eliminar(Long id) {
        ContenidoItem contenido = contenidoItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contenido no encontrado"));

        // Eliminar el archivo si existe
        if (contenido.getArchivo() != null) {
            archivoRepository.deleteById(contenido.getArchivo().getId());
        }

        // Eliminar el contenido principal
        contenidoItemRepository.deleteById(id);
    }

    @Override
    public List<ContenidoItemDto> obtenerPorAlumno(Long alumnoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));
        return alumno.getContenidos().stream()
                .map(contenidoItemMapper::toContenidoItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContenidoItemDto crearParaAlumno(Long alumnoId, String texto, String tipoContenido, String mes, MultipartFile file) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new NotFoundException("Alumno no encontrado"));

        Mes mesObj = mesRepository.findByNombreIgnoreCaseAndGrupoIdCustom(mes, alumno.getGrupo().getId())
                .orElseThrow(() -> new NotFoundException("Mes no encontrado en el grupo del alumno"));

        ArchivoDto archivoDto = archivoService.subirArchivo(file);
        Archivo archivo = archivoRepository.findById(archivoDto.getId().longValue())
                .orElseThrow(() -> new NotFoundException("Archivo no encontrado"));

        ContenidoItem contenido = ContenidoItem.builder()
                .texto(texto)
                .tipoContenido(TipoContenido.valueOf(tipoContenido))
                .mes(mesObj)
                .autor(alumno)
                .archivo(archivo)
                .build();

        return contenidoItemMapper.toContenidoItemDto(contenidoItemRepository.save(contenido));
    }

    @Override
    public List<ContenidoItemDto> obtenerPorGrupoYMes(Long grupoId, String mes) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));

        Long mesId = Long.valueOf(mes);

        return contenidoItemRepository.findAll().stream()
                .filter(c -> c.getMes() != null &&
                        c.getMes().getGrupo().getId().equals(grupoId) &&
                        c.getMes().getId().equals(mesId))
                .map(contenidoItemMapper::toContenidoItemDto)
                .collect(Collectors.toList());
    }


    @Override
    public ContenidoItemDto crearParaGrupoPorMes(Long grupoId, Long mesId, ContenidoItemDto contenidoDto, MultipartFile file) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));

        Usuario autor = usuarioRepository.findById(Long.valueOf(contenidoDto.getAutorId()))
                .orElseThrow(() -> new NotFoundException("Autor no encontrado"));

        Mes mesObj = mesRepository.findById(mesId)
                .filter(m -> m.getGrupo().getId().equals(grupoId))
                .orElseThrow(() -> new NotFoundException("Mes no pertenece al grupo"));

        ContenidoItem contenido = contenidoItemMapper.toContenidoItemEntity(contenidoDto);
        contenido.setAutor(autor);
        contenido.setMes(mesObj);

        if (file != null && !file.isEmpty()) {
            ArchivoDto archivoDto = archivoService.subirArchivo(file);
            Archivo archivo = archivoMapper.toArchivoEntity(archivoDto);
            contenido.setArchivo(archivo);
        }

        return contenidoItemMapper.toContenidoItemDto(contenidoItemRepository.save(contenido));
    }


}