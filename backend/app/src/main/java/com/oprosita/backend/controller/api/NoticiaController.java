package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.NoticiasApi;
import com.oprosita.backend.dto.NoticiaDto;
import com.oprosita.backend.mapper.NoticiaMapper;
import com.oprosita.backend.model.generated.Noticia;
import com.oprosita.backend.service.NoticiaService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NoticiaController implements NoticiasApi {

    private final NoticiaService noticiaService;
    private final NoticiaMapper mapper;

    @Override
    public ResponseEntity<Void> deleteNoticia(Integer id) {
        noticiaService.eliminar(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Noticia>> getNoticias() {
        List<NoticiaDto> dtos = noticiaService.obtenerTodos();
        List<Noticia> noticias = dtos.stream()
                .map(mapper::toGeneratedNoticia)
                .collect(Collectors.toList());
        return ResponseEntity.ok(noticias);
    }

    @Override
    public ResponseEntity<Noticia> createNoticia(
            @Parameter(name = "descripcion", description = "")
            @Valid @RequestParam(value = "descripcion", required = false) String descripcion,

            @Parameter(name = "file", description = "")
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        NoticiaDto dto = noticiaService.crearNoticia(descripcion, null, file);
        return ResponseEntity.status(201).body(mapper.toGeneratedNoticia(dto));
    }
}
