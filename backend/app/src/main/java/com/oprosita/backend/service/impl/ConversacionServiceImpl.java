package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ConversacionDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.ConversacionMapper;
import com.oprosita.backend.model.Conversacion;
import com.oprosita.backend.repository.ConversacionRepository;
import com.oprosita.backend.service.ConversacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ConversacionServiceImpl implements ConversacionService {

    private final ConversacionRepository conversacionRepository;
    private final ConversacionMapper mapper;

    @Override
    public List<ConversacionDto> obtenerPorUsuario(Long usuarioId) {
        List<Conversacion> conversaciones = conversacionRepository.findAll().stream()
                .filter(c -> c.getUsuarioId().equals(usuarioId) || c.getOtroUsuarioId().equals(usuarioId))
                .collect(Collectors.toList());

        if (conversaciones.isEmpty()) {
            throw new NotFoundException("No se encontraron conversaciones para el usuario con ID: " + usuarioId);
        }

        return mapper.toConversacionDtoList(conversaciones);
    }
}
