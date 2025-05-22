package com.oprosita.backend.dto;

import com.oprosita.backend.model.generated.ContenidoItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContenidoItemDto {
    private Integer id;
    private String texto;
    private String value;
    private ContenidoItem.TipoContenidoEnum tipoContenido;
    private Integer alumnoId;
    private Integer grupoId;
    private String mes;
    private Integer archivoId;
}
