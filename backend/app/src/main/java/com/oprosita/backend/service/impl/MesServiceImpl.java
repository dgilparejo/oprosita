package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.MesDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.MesMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.Mes;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.repository.MesRepository;
import com.oprosita.backend.service.MesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MesServiceImpl implements MesService {

    private final MesRepository mesRepository;
    private final GrupoRepository grupoRepository;
    private final MesMapper mapper;

    @Override
    public MesDto obtenerPorId(Long id) {
        Mes mes = mesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mes no encontrado"));
        return mapper.toMesDto(mes);
    }

    @Override
    public List<MesDto> obtenerTodos() {
        return mesRepository.findAll().stream()
                .map(mapper::toMesDto)
                .collect(Collectors.toList());
    }

    @Override
    public MesDto crear(MesDto dto) {
        Mes mes = mapper.toMesEntity(dto);
        mes = mesRepository.save(mes);
        return mapper.toMesDto(mes);
    }

    @Override
    public MesDto actualizar(Long id, MesDto dto) {
        if (!mesRepository.existsById(id)) {
            throw new NotFoundException("Mes no encontrado");
        }
        Mes mes = mapper.toMesEntity(dto);
        mes.setId(id);
        mes = mesRepository.save(mes);
        return mapper.toMesDto(mes);
    }

    @Override
    public void eliminar(Long id) {
        if (!mesRepository.existsById(id)) {
            throw new NotFoundException("Mes no encontrado");
        }
        mesRepository.deleteById(id);
    }

    @Override
    public List<MesDto> obtenerMesesPorGrupo(Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        return grupo.getMeses().stream()
                .map(mapper::toMesDto)
                .collect(Collectors.toList());
    }
}
