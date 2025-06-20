package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.dto.NoticiaDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.NoticiaMapper;
import com.oprosita.backend.model.Archivo;
import com.oprosita.backend.model.Noticia;
import com.oprosita.backend.repository.ArchivoRepository;
import com.oprosita.backend.repository.NoticiaRepository;
import com.oprosita.backend.service.ArchivoService;
import com.oprosita.backend.service.NoticiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticiaServiceImpl implements NoticiaService {

    private final NoticiaRepository noticiaRepository;
    private final ArchivoService archivoService;
    private final ArchivoRepository archivoRepository;
    private final NoticiaMapper mapper;

    @Override
    public NoticiaDto obtenerPorId(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Noticia no encontrada"));
        return mapper.toNoticiaDto(noticia);
    }

    @Override
    public List<NoticiaDto> obtenerTodos() {
        return noticiaRepository.findAll().stream()
                .map(mapper::toNoticiaDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoticiaDto crear(NoticiaDto dto) {
        Noticia noticia = mapper.toNoticiaEntity(dto);
        noticia = noticiaRepository.save(noticia);
        return mapper.toNoticiaDto(noticia);
    }

    @Override
    public NoticiaDto actualizar(Long id, NoticiaDto dto) {
        if (!noticiaRepository.existsById(id)) {
            throw new NotFoundException("Noticia no encontrada");
        }
        Noticia noticia = mapper.toNoticiaEntity(dto);
        noticia.setId(id);
        noticia = noticiaRepository.save(noticia);
        return mapper.toNoticiaDto(noticia);
    }

    @Override
    public void eliminar(Long id) {
        if (!noticiaRepository.existsById(id)) {
            throw new NotFoundException("Noticia no encontrada");
        }
        noticiaRepository.deleteById(id);
    }

    @Override
    public NoticiaDto crearNoticia(String descripcion, URI url, MultipartFile file) {
        Archivo archivo = null;

        if (file != null && !file.isEmpty()) {
            ArchivoDto archivoDto = archivoService.subirArchivo(file);
            archivo = archivoRepository.findById(archivoDto.getId().longValue())
                    .orElseThrow(() -> new NotFoundException("Archivo subido no encontrado"));
        }

        Noticia.NoticiaBuilder builder = Noticia.builder();

        builder.descripcion((descripcion != null && !descripcion.isBlank()) ? descripcion : "");
        builder.url(url);
        builder.archivo(archivo);

        Noticia noticia = noticiaRepository.save(builder.build());
        return mapper.toNoticiaDto(noticia);
    }
}
