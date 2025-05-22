package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.dto.NoticiaDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.Noticia;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.repository.NoticiaRepository;
import com.oprosita.backend.service.ArchivoService;
import com.oprosita.backend.service.NoticiaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoticiaServiceImpl implements NoticiaService {

    private final NoticiaRepository noticiaRepository;
    private final GrupoRepository grupoRepository;
    private final ArchivoService archivoService;
    private final GeneralMapper generalMapper;

    public NoticiaServiceImpl(NoticiaRepository noticiaRepository,
                              GrupoRepository grupoRepository,
                              ArchivoService archivoService,
                              GeneralMapper generalMapper) {
        this.noticiaRepository = noticiaRepository;
        this.grupoRepository = grupoRepository;
        this.archivoService = archivoService;
        this.generalMapper = generalMapper;
    }

    @Override
    public NoticiaDto obtenerPorId(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Noticia no encontrada"));
        return generalMapper.toDto(noticia);
    }

    @Override
    public List<NoticiaDto> obtenerTodos() {
        return noticiaRepository.findAll().stream()
                .map(generalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoticiaDto crear(NoticiaDto dto) {
        Noticia noticia = generalMapper.toEntity(dto);
        if (dto.getGrupoId() != null) {
            Grupo grupo = grupoRepository.findById(dto.getGrupoId().longValue())
                    .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            noticia.setGrupo(grupo);
        }
        noticia = noticiaRepository.save(noticia);
        return generalMapper.toDto(noticia);
    }

    @Override
    public NoticiaDto actualizar(Long id, NoticiaDto dto) {
        if (!noticiaRepository.existsById(id)) {
            throw new NotFoundException("Noticia no encontrada");
        }
        Noticia noticia = generalMapper.toEntity(dto);
        noticia.setId(id);
        if (dto.getGrupoId() != null) {
            Grupo grupo = grupoRepository.findById(dto.getGrupoId().longValue())
                    .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            noticia.setGrupo(grupo);
        }
        noticia = noticiaRepository.save(noticia);
        return generalMapper.toDto(noticia);
    }

    @Override
    public void eliminar(Long id) {
        if (!noticiaRepository.existsById(id)) {
            throw new NotFoundException("Noticia no encontrada");
        }
        noticiaRepository.deleteById(id);
    }

    @Override
    public NoticiaDto crearNoticia(String descripcion, Long grupoId, MultipartFile file) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));

        ArchivoDto archivoDto = archivoService.subirArchivo(file);
        Long archivoId = archivoDto.getId().longValue();

        Noticia noticia = Noticia.builder()
                .descripcion(descripcion)
                .archivoId(archivoId)
                .grupo(grupo)
                .build();

        noticia = noticiaRepository.save(noticia);
        return generalMapper.toDto(noticia);
    }
}
