package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.MensajeDto;
import com.oprosita.backend.dto.ConversacionDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Mensaje;
import com.oprosita.backend.repository.MensajeRepository;
import com.oprosita.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService {

    private final MensajeRepository mensajeRepository;
    private final GeneralMapper mapper;

    @Override
    public MensajeDto obtenerMensajePorId(Long id) {
        Mensaje mensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mensaje no encontrado"));
        return mapper.toMensajeDto(mensaje);
    }

    @Override
    public List<MensajeDto> obtenerMensajes(Long remitente, Long destinatario) {
        List<Mensaje> mensajes = mensajeRepository.findByRemitenteIdAndDestinatarioId(remitente, destinatario);
        return mensajes.stream()
                .map(mapper::toMensajeDto)
                .collect(Collectors.toList());
    }

    @Override
    public MensajeDto enviarMensaje(MensajeDto mensajeDto) {
        Mensaje mensaje = mapper.toMensajeEntity(mensajeDto);
        mensaje.setLeido(false);
        return mapper.toMensajeDto(mensajeRepository.save(mensaje));
    }

    @Override
    public void marcarComoLeido(Long mensajeId) {
        Mensaje mensaje = mensajeRepository.findById(mensajeId)
                .orElseThrow(() -> new NotFoundException("Mensaje no encontrado"));
        mensaje.setLeido(true);
        mensajeRepository.save(mensaje);
    }

    @Override
    public List<ConversacionDto> obtenerConversacionesPorUsuario(Long usuarioId) {
        // Esto depende de tu lógica de negocio. A modo de ejemplo:
        List<Mensaje> mensajes = mensajeRepository.findConversacionesByUsuarioId(usuarioId);
        return mensajes.stream()
                .map(m -> ConversacionDto.builder()
                        .usuarioId(m.getRemitente().getId().equals(usuarioId) ? m.getDestinatario().getId() : m.getRemitente().getId())
                        .ultimoMensaje(mapper.toMensajeDto(m))
                        .build())
                .collect(Collectors.toList());
    }
}
