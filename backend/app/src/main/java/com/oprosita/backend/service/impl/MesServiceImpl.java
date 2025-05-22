package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.MesDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.Mes;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.repository.MesRepository;
import com.oprosita.backend.service.MesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MesServiceImpl implements MesService {

    private final MesRepository mesRepository;
    private final GrupoRepository grupoRepository;
    private final GeneralMapper generalMapper;

    public MesServiceImpl(MesRepository mesRepository,
                          GrupoRepository grupoRepository,
                          GeneralMapper generalMapper) {
        this.mesRepository = mesRepository;
        this.grupoRepository = grupoRepository;
        this.generalMapper = generalMapper;
    }

    @Override
    public MesDto obtenerPorId(Long id) {
        Mes mes = mesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mes no encontrado"));
        return generalMapper.toDto(mes);
    }

    @Override
    public List<MesDto> obtenerTodos() {
        return mesRepository.findAll().stream()
                .map(generalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MesDto crear(MesDto dto) {
        Mes mes = generalMapper.toEntity(dto);
        mes = mesRepository.save(mes);
        return generalMapper.toDto(mes);
    }

    @Override
    public MesDto actualizar(Long id, MesDto dto) {
        if (!mesRepository.existsById(id)) {
            throw new NotFoundException("Mes no encontrado");
        }
        Mes mes = generalMapper.toEntity(dto);
        mes.setId(id);
        mes = mesRepository.save(mes);
        return generalMapper.toDto(mes);
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
                .map(generalMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MesDto agregarMesAGrupo(Long grupoId, MesDto mesDto) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado"));
        Mes mes = generalMapper.toEntity(mesDto);
        mes.setGrupo(grupo);
        mes = mesRepository.save(mes);
        return generalMapper.toDto(mes);
    }
}
