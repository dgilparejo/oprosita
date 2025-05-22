package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.ChatApi;
import com.oprosita.backend.dto.ConversacionDto;
import com.oprosita.backend.dto.MensajeDto;
import com.oprosita.backend.mapper.GeneralMapper;
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
    private final GeneralMapper mapper;

    @Override
    public ResponseEntity<Void> enviarMensaje(Mensaje mensaje) {
        MensajeDto mensajeDto = mapper.toMensajeDto(mensaje);
        chatService.enviarMensaje(mensajeDto);
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<List<MensajeDto>> getMensajes(Integer remitente, Integer destinatario) {
        Long rem = remitente != null ? remitente.longValue() : null;
        Long dest = destinatario != null ? destinatario.longValue() : null;
        List<MensajeDto> mensajes = chatService.obtenerMensajes(rem, dest);
        return ResponseEntity.ok(mensajes);
    }

    @Override
    public ResponseEntity<List<ConversacionDto>> getConversaciones(Integer usuarioId) {
        return ResponseEntity.ok(chatService.obtenerConversacionesPorUsuario(usuarioId.longValue()));
    }
}
