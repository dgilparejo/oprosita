package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.ContenidoApi;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.mapper.ContenidoItemMapper;
import com.oprosita.backend.model.ContenidoItem;
import com.oprosita.backend.service.ContenidoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContenidoItemController implements ContenidoApi {

    private final ContenidoItemService contenidoItemService;
    private final ContenidoItemMapper mapper;

    @Override
    public ResponseEntity<Void> deleteContenido(Integer id) {
        contenidoItemService.eliminar(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateContenido(Integer id, com.oprosita.backend.model.generated.ContenidoItem contenidoItem) {
        ContenidoItem entidadInterna = mapper.toContenidoItemEntity(contenidoItem);
        ContenidoItemDto dto = mapper.toContenidoItemDto(entidadInterna);
        contenidoItemService.actualizar(id.longValue(), dto);
        return ResponseEntity.ok().build();
    }
}
