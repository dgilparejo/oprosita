package com.oprosita.backend.util;

import com.oprosita.backend.dto.MensajeDto;
import com.oprosita.backend.model.Archivo;
import com.oprosita.backend.model.Mes;
import com.oprosita.backend.model.TipoDestinatario;
import com.oprosita.backend.model.Usuario;
import com.oprosita.backend.model.generated.Alumno;
import com.oprosita.backend.model.generated.Profesor;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MapperUtil {

    public String map(MensajeDto mensaje) {
        if (mensaje == null) return null;
        return mensaje.getContenido();
    }

    public MensajeDto map(String contenido) {
        if (contenido == null) return null;
        return MensajeDto.builder().contenido(contenido).build();
    }

    public Integer map(JsonNullable<Integer> value) {
        return value != null && value.isPresent() ? value.get() : null;
    }

    public JsonNullable<Integer> map(Integer value) {
        return value != null ? JsonNullable.of(value) : JsonNullable.undefined();
    }

    public String offsetDateTimeToString(OffsetDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null;
    }

    public OffsetDateTime stringToOffsetDateTime(String dateTime) {
        return dateTime != null ? OffsetDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null;
    }

    // === ALUMNO ===
    public Alumno.TipoEnum toGeneratedAlumnoTipoEnum(TipoDestinatario tipo) {
        if (tipo == null) return null;
        return switch (tipo) {
            case ALUMNO -> Alumno.TipoEnum.ALUMNO;
            default -> throw new IllegalArgumentException("Tipo no mapeado (Alumno): " + tipo);
        };
    }

    public TipoDestinatario toEntityTipoDestinatario(Alumno.TipoEnum tipoEnum) {
        if (tipoEnum == null) return null;
        return switch (tipoEnum) {
            case ALUMNO -> TipoDestinatario.ALUMNO;
            default -> throw new IllegalArgumentException("TipoEnum no mapeado (Alumno): " + tipoEnum);
        };
    }

    // === PROFESOR ===
    public Profesor.TipoEnum toGeneratedProfesorTipoEnum(TipoDestinatario tipo) {
        if (tipo == null) return null;
        return switch (tipo) {
            case PROFESOR -> Profesor.TipoEnum.PROFESOR;
            default -> throw new IllegalArgumentException("Tipo no mapeado (Profesor): " + tipo);
        };
    }

    public TipoDestinatario toEntityTipoDestinatario(Profesor.TipoEnum tipoEnum) {
        if (tipoEnum == null) return null;
        return switch (tipoEnum) {
            case PROFESOR -> TipoDestinatario.PROFESOR;
            default -> throw new IllegalArgumentException("TipoEnum no mapeado (Profesor): " + tipoEnum);
        };
    }

    public Archivo mapToArchivo(Integer id) {
        if (id == null) return null;
        Archivo archivo = new Archivo();
        archivo.setId(id.longValue());
        return archivo;
    }

    public Mes mapToMes(Integer id) {
        if (id == null) return null;
        Mes mes = new Mes();
        mes.setId(id.longValue());
        return mes;
    }
}
