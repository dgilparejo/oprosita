package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.dto.SimulacroDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.Simulacro;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.repository.SimulacroRepository;
import com.oprosita.backend.service.SimulacroService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SimulacroServiceImpl implements SimulacroService {

    private final SimulacroRepository simulacroRepository;
    private final GrupoRepository grupoRepository;
    private final GeneralMapper generalMapper;
    private final ArchivoServiceImpl archivoService;

    public SimulacroServiceImpl(SimulacroRepository simulacroRepository, GrupoRepository grupoRepository, GeneralMapper generalMapper, ArchivoServiceImpl archivoService) {
        this.simulacroRepository = simulacroRepository;
        this.grupoRepository = grupoRepository;
        this.generalMapper = generalMapper;
        this.archivoService = archivoService;
    }

    @Override
    public SimulacroDto obtenerPorId(Long id) {
        Simulacro simulacro = simulacroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Simulacro no encontrado"));
        return generalMapper.toDto(simulacro);
    }

    @Override
    public List<SimulacroDto> obtenerTodos() {
        return simulacroRepository.findAll().stream()
                .map(generalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SimulacroDto crear(SimulacroDto dto) {
        Simulacro simulacro = generalMapper.toEntity(dto);
        if (dto.getGrupoId() != null) {
            Grupo grupo = grupoRepository.findById(dto.getGrupoId().longValue())
                    .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            simulacro.setGrupo(grupo);
        }
        simulacro = simulacroRepository.save(simulacro);
        return generalMapper.toDto(simulacro);
    }

    @Override
    public SimulacroDto actualizar(Long id, SimulacroDto dto) {
        if (!simulacroRepository.existsById(id)) {
            throw new NotFoundException("Simulacro no encontrado");
        }
        Simulacro simulacro = generalMapper.toEntity(dto);
        simulacro.setId(id);
        if (dto.getGrupoId() != null) {
            Grupo grupo = grupoRepository.findById(dto.getGrupoId().longValue())
                    .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
            simulacro.setGrupo(grupo);
        }
        simulacro = simulacroRepository.save(simulacro);
        return generalMapper.toDto(simulacro);
    }

    @Override
    public void eliminar(Long id) {
        if (!simulacroRepository.existsById(id)) {
            throw new NotFoundException("Simulacro no encontrado");
        }
        simulacroRepository.deleteById(id);
    }

    @Override
    public SimulacroDto crearSimulacro(String descripcion, Long grupoId, MultipartFile file) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));

        // Subimos el archivo real y obtenemos su ID
        ArchivoDto archivoDto = archivoService.subirArchivo(file);
        Long archivoId = archivoDto.getId().longValue();

        Simulacro simulacro = Simulacro.builder()
                .descripcion(descripcion)
                .archivoId(archivoId)
                .grupo(grupo)
                .build();

        simulacro = simulacroRepository.save(simulacro);
        return generalMapper.toDto(simulacro);
    }
}
