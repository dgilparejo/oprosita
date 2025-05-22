package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Archivo;
import com.oprosita.backend.repository.ArchivoRepository;
import com.oprosita.backend.service.ArchivoService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoRepository archivoRepository;
    private final GeneralMapper generalMapper;

    public ArchivoServiceImpl(ArchivoRepository archivoRepository, GeneralMapper generalMapper) {
        this.archivoRepository = archivoRepository;
        this.generalMapper = generalMapper;
    }

    @Override
    public ArchivoDto obtenerPorId(Long id) {
        Archivo archivo = archivoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Archivo no encontrado"));
        return generalMapper.toArchivoDto(archivo);
    }

    @Override
    public List<ArchivoDto> obtenerTodos() {
        return archivoRepository.findAll().stream()
                .map(generalMapper::toArchivoDto)
                .collect(Collectors.toList());
    }

    @Override
    public ArchivoDto crear(ArchivoDto dto) {
        Archivo archivo = generalMapper.toArchivoEntity(dto);
        archivo = archivoRepository.save(archivo);
        return generalMapper.toArchivoDto(archivo);
    }

    @Override
    public ArchivoDto actualizar(Long id, ArchivoDto dto) {
        if (!archivoRepository.existsById(id)) {
            throw new NotFoundException("Archivo no encontrado");
        }
        Archivo archivo = generalMapper.toArchivoEntity(dto);
        archivo.setId(id);
        archivo = archivoRepository.save(archivo);
        return generalMapper.toArchivoDto(archivo);
    }

    @Override
    public void eliminar(Long id) {
        if (!archivoRepository.existsById(id)) {
            throw new NotFoundException("Archivo no encontrado");
        }
        archivoRepository.deleteById(id);
    }

    @Override
    public ArchivoDto subirArchivo(MultipartFile file) {
        try {
            Archivo archivo = Archivo.builder()
                    .nombre(file.getOriginalFilename())
                    .tipo(file.getContentType())
                    .datos(file.getBytes())
                    .url("/archivos/" + file.getOriginalFilename())
                    .build();

            archivo = archivoRepository.save(archivo);
            return generalMapper.toArchivoDto(archivo);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo", e);
        }
    }

    @Override
    public Resource descargarArchivo(Long id) {
        Archivo archivo = archivoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Archivo no encontrado"));
        return new ByteArrayResource(archivo.getDatos());
    }
}
