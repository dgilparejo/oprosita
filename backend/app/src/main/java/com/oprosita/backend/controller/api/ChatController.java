package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.ChatApi;
import com.oprosita.backend.dto.ConversacionDto;
import com.oprosita.backend.dto.MensajeDto;
import com.oprosita.backend.mapper.ConversacionMapper;
import com.oprosita.backend.mapper.MensajeMapper;
import com.oprosita.backend.model.generated.Conversacion;
import com.oprosita.backend.model.generated.Mensaje;
import com.oprosita.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController implements ChatApi {

    private final ChatService chatService;
    private final MensajeMapper mensajeMapper;
    private final ConversacionMapper conversacionMapper;

    @Override
    public ResponseEntity<Mensaje> enviarMensaje(Mensaje mensaje) {
        MensajeDto mensajeDto = mensajeMapper.fromGeneratedMensaje(mensaje);
        MensajeDto creado = chatService.enviarMensaje(mensajeDto);
        Mensaje respuesta = mensajeMapper.toGeneratedMensaje(creado);

        return ResponseEntity.status(201).body(respuesta);
    }

    @Override
    public ResponseEntity<List<Mensaje>> getMensajes(Integer remitente, Integer destinatario) {
        Long rem = remitente != null ? remitente.longValue() : null;
        Long dest = destinatario != null ? destinatario.longValue() : null;
        List<MensajeDto> dtos = chatService.obtenerMensajes(rem, dest);
        List<Mensaje> mensajes = dtos.stream()
                .map(mensajeMapper::toGeneratedMensaje)
                .toList();
        return ResponseEntity.ok(mensajes);
    }

    @Override
    public ResponseEntity<List<Conversacion>> getConversaciones(Integer usuarioId) {
        List<ConversacionDto> dtos = chatService.obtenerConversacionesPorUsuario(usuarioId.longValue());
        List<Conversacion> conversaciones = dtos.stream()
                .map(conversacionMapper::toGeneratedConversacion)
                .toList();
        return ResponseEntity.ok(conversaciones);
    }
}
