package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.dto.SimulacroDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.SimulacroMapper;
import com.oprosita.backend.model.Simulacro;
import com.oprosita.backend.repository.SimulacroRepository;
import com.oprosita.backend.service.ArchivoService;
import com.oprosita.backend.service.SimulacroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SimulacroServiceImpl implements SimulacroService {

    private final SimulacroRepository simulacroRepository;
    private final SimulacroMapper mapper;
    private final ArchivoService archivoService;

    @Override
    public SimulacroDto obtenerPorId(Long id) {
        Simulacro simulacro = simulacroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Simulacro no encontrado"));
        return mapper.toSimulacroDto(simulacro);
    }

    @Override
    public List<SimulacroDto> obtenerTodos() {
        return simulacroRepository.findAll().stream()
                .map(mapper::toSimulacroDto)
                .collect(Collectors.toList());
    }

    @Override
    public SimulacroDto crear(SimulacroDto dto) {
        Simulacro simulacro = mapper.toSimulacroEntity(dto);
        simulacro = simulacroRepository.save(simulacro);
        return mapper.toSimulacroDto(simulacro);
    }

    @Override
    public SimulacroDto actualizar(Long id, SimulacroDto dto) {
        if (!simulacroRepository.existsById(id)) {
            throw new NotFoundException("Simulacro no encontrado");
        }
        Simulacro simulacro = mapper.toSimulacroEntity(dto);
        simulacro.setId(id);
        simulacro = simulacroRepository.save(simulacro);
        return mapper.toSimulacroDto(simulacro);
    }

    @Override
    public void eliminar(Long id) {
        if (!simulacroRepository.existsById(id)) {
            throw new NotFoundException("Simulacro no encontrado");
        }
        simulacroRepository.deleteById(id);
    }

    @Override
    public SimulacroDto crearSimulacro(String descripcion, MultipartFile file) {
        ArchivoDto archivoDto = archivoService.subirArchivo(file);
        Long archivoId = archivoDto.getId().longValue();

        Simulacro simulacro = Simulacro.builder()
                .descripcion(descripcion)
                .build();

        simulacro = simulacroRepository.save(simulacro);
        return mapper.toSimulacroDto(simulacro);
    }
}
