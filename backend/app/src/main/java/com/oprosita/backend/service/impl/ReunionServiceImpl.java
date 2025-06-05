package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ReunionDto;
import com.oprosita.backend.exception.ConflictException;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.ReunionMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.Reunion;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.repository.ReunionRepository;
import com.oprosita.backend.service.ReunionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReunionServiceImpl implements ReunionService {

    private final ReunionRepository reunionRepository;
    private final GrupoRepository grupoRepository;
    private final ReunionMapper mapper;

    @Override
    public ReunionDto obtenerPorId(Long id) {
        Reunion reunion = reunionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reunión no encontrada"));
        return mapper.toReunionDto(reunion);
    }

    @Override
    public List<ReunionDto> obtenerTodos() {
        return reunionRepository.findAll().stream()
                .map(mapper::toReunionDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReunionDto crear(ReunionDto dto) {
        Reunion reunion = mapper.toReunionEntity(dto);
        reunion.setFechaHora(dto.getFechaHora());
        return mapper.toReunionDto(reunionRepository.save(reunion));
    }

    @Override
    public ReunionDto actualizar(Long id, ReunionDto dto) {
        if (!reunionRepository.existsById(id)) {
            throw new NotFoundException("Reunión no encontrada");
        }

        Reunion reunion = mapper.toReunionEntity(dto);
        reunion.setId(id);
        reunion.setFechaHora(dto.getFechaHora());
        return mapper.toReunionDto(reunionRepository.save(reunion));
    }

    @Override
    public void eliminar(Long id) {
        Reunion reunion = reunionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reunión no encontrada"));

        // Eliminar la referencia al grupo asociado, si existe
        Grupo grupo = reunion.getGrupo();
        if (grupo != null) {
            grupo.setReunion(null);
        }

        reunionRepository.delete(reunion);
    }

    @Override
    public List<ReunionDto> obtenerReunionesPorGrupo(Long grupoId) {
        return reunionRepository.findAll().stream()
                .filter(r -> r.getGrupo() != null && r.getGrupo().getId().equals(grupoId))
                .map(mapper::toReunionDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReunionDto crearReunionParaGrupo(Long grupoId, ReunionDto reunionDto) {
        if (reunionRepository.existsByGrupoId(grupoId)) {
            throw new ConflictException("Ya existe una reunión para el grupo con ID: " + grupoId);
        }

        Reunion reunion = mapper.toReunionEntity(reunionDto);

        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        reunion.setGrupo(grupo);

        reunion.setFechaHora(reunionDto.getFechaHora());
        return mapper.toReunionDto(reunionRepository.save(reunion));
    }
}
