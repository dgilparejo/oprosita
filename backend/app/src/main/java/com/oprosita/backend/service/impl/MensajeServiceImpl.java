package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.MensajeDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Mensaje;
import com.oprosita.backend.repository.MensajeRepository;
import com.oprosita.backend.service.MensajeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MensajeServiceImpl implements MensajeService {

    private final MensajeRepository mensajeRepository;
    private final GeneralMapper generalMapper;

    public MensajeServiceImpl(MensajeRepository mensajeRepository, GeneralMapper generalMapper) {
        this.mensajeRepository = mensajeRepository;
        this.generalMapper = generalMapper;
    }

    @Override
    public MensajeDto obtenerPorId(Long id) {
        Mensaje mensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mensaje no encontrado"));
        return generalMapper.toDto(mensaje);
    }

    @Override
    public List<MensajeDto> obtenerPorConversacion(Long remitente, Long destinatario) {
        List<Mensaje> mensajes = mensajeRepository.findAll().stream()
                .filter(m -> m.getRemitente().equals(remitente)
                        && m.getDestinatario().equals(destinatario))
                .collect(Collectors.toList());
        return generalMapper.toDtoList(mensajes);
    }

    @Override
    public MensajeDto enviar(MensajeDto mensajeDto) {
        Mensaje mensaje = generalMapper.toEntity(mensajeDto);
        mensaje = mensajeRepository.save(mensaje);
        return generalMapper.toDto(mensaje);
    }

    @Override
    public void marcarComoLeido(Long mensajeId) {
        Mensaje mensaje = mensajeRepository.findById(mensajeId)
                .orElseThrow(() -> new NotFoundException("Mensaje no encontrado"));
        mensaje.setLeido(true);
        mensajeRepository.save(mensaje);
    }
}
