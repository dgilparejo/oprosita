package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ConversacionDto;
import com.oprosita.backend.dto.MensajeDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.MensajeMapper;
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
    private final MensajeMapper mensajeMapper;

    @Override
    public MensajeDto obtenerMensajePorId(Long id) {
        Mensaje mensaje = mensajeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mensaje no encontrado"));
        return mensajeMapper.toMensajeDto(mensaje);
    }

    @Override
    public List<MensajeDto> obtenerMensajes(Long remitente, Long destinatario) {
        List<Mensaje> mensajes = mensajeRepository.findByRemitenteAndDestinatario(remitente, destinatario);
        return mensajes.stream()
                .map(mensajeMapper::toMensajeDto)
                .collect(Collectors.toList());
    }

    @Override
    public MensajeDto enviarMensaje(MensajeDto mensajeDto) {
        Mensaje mensaje = mensajeMapper.toMensajeEntity(mensajeDto);
        mensaje.setLeido(false);
        return mensajeMapper.toMensajeDto(mensajeRepository.save(mensaje));
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
        List<Mensaje> mensajes = mensajeRepository.findConversacionesByUsuarioId(usuarioId);
        return mensajes.stream()
                .map(m -> ConversacionDto.builder()
                        .usuarioId((m.getRemitente().equals(usuarioId) ? m.getDestinatario() : m.getRemitente()).intValue())
                        .ultimoMensaje(m.getContenido())
                        .build())
                .collect(Collectors.toList());
    }
}
