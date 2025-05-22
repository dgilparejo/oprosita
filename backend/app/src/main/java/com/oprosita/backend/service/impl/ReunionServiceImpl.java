package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ReunionDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Reunion;
import com.oprosita.backend.repository.ReunionRepository;
import com.oprosita.backend.service.ReunionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReunionServiceImpl implements ReunionService {

    private final ReunionRepository reunionRepository;
    private final GeneralMapper generalMapper;

    public ReunionServiceImpl(ReunionRepository reunionRepository, GeneralMapper generalMapper) {
        this.reunionRepository = reunionRepository;
        this.generalMapper = generalMapper;
    }

    @Override
    public ReunionDto obtenerPorId(Long id) {
        Reunion reunion = reunionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reunión no encontrada"));
        return generalMapper.toDto(reunion);
    }

    @Override
    public List<ReunionDto> obtenerTodos() {
        return reunionRepository.findAll().stream()
                .map(generalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReunionDto crear(ReunionDto dto) {
        Reunion reunion = generalMapper.toEntity(dto);
        reunion.setFechaHora(OffsetDateTime.parse(dto.getFechaHora(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return generalMapper.toDto(reunionRepository.save(reunion));
    }

    @Override
    public ReunionDto actualizar(Long id, ReunionDto dto) {
        if (!reunionRepository.existsById(id)) {
            throw new NotFoundException("Reunión no encontrada");
        }

        Reunion reunion = generalMapper.toEntity(dto);
        reunion.setId(id);
        reunion.setFechaHora(OffsetDateTime.parse(dto.getFechaHora(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return generalMapper.toDto(reunionRepository.save(reunion));
    }

    @Override
    public void eliminar(Long id) {
        if (!reunionRepository.existsById(id)) {
            throw new NotFoundException("Reunión no encontrada");
        }
        reunionRepository.deleteById(id);
    }

    @Override
    public List<ReunionDto> obtenerReunionesPorGrupo(Long grupoId) {
        return reunionRepository.findAll().stream()
                .filter(r -> r.getGrupoId().equals(grupoId))
                .map(generalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReunionDto crearReunionParaGrupo(Long grupoId, ReunionDto reunionDto) {
        Reunion reunion = generalMapper.toEntity(reunionDto);
        reunion.setGrupoId(grupoId);
        reunion.setFechaHora(OffsetDateTime.parse(reunionDto.getFechaHora(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return generalMapper.toDto(reunionRepository.save(reunion));
    }
}
