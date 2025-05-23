package com.oprosita.backend.model.generated;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Novedad
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-23T22:26:08.390066400+02:00[Europe/Madrid]", comments = "Generator version: 7.5.0")
public class Novedad {

  private Integer id;

  private String texto;

  /**
   * Gets or Sets tipoDestinatario
   */
  public enum TipoDestinatarioEnum {
    PROFESOR("profesor"),
    
    ALUMNO("alumno");

    private String value;

    TipoDestinatarioEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TipoDestinatarioEnum fromValue(String value) {
      for (TipoDestinatarioEnum b : TipoDestinatarioEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TipoDestinatarioEnum tipoDestinatario;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime fechaCreacion;

  public Novedad id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Novedad texto(String texto) {
    this.texto = texto;
    return this;
  }

  /**
   * Get texto
   * @return texto
  */
  
  @Schema(name = "texto", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("texto")
  public String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  public Novedad tipoDestinatario(TipoDestinatarioEnum tipoDestinatario) {
    this.tipoDestinatario = tipoDestinatario;
    return this;
  }

  /**
   * Get tipoDestinatario
   * @return tipoDestinatario
  */
  
  @Schema(name = "tipoDestinatario", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tipoDestinatario")
  public TipoDestinatarioEnum getTipoDestinatario() {
    return tipoDestinatario;
  }

  public void setTipoDestinatario(TipoDestinatarioEnum tipoDestinatario) {
    this.tipoDestinatario = tipoDestinatario;
  }

  public Novedad fechaCreacion(OffsetDateTime fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
    return this;
  }

  /**
   * Get fechaCreacion
   * @return fechaCreacion
  */
  @Valid 
  @Schema(name = "fechaCreacion", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fechaCreacion")
  public OffsetDateTime getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(OffsetDateTime fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Novedad novedad = (Novedad) o;
    return Objects.equals(this.id, novedad.id) &&
        Objects.equals(this.texto, novedad.texto) &&
        Objects.equals(this.tipoDestinatario, novedad.tipoDestinatario) &&
        Objects.equals(this.fechaCreacion, novedad.fechaCreacion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, texto, tipoDestinatario, fechaCreacion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Novedad {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    texto: ").append(toIndentedString(texto)).append("\n");
    sb.append("    tipoDestinatario: ").append(toIndentedString(tipoDestinatario)).append("\n");
    sb.append("    fechaCreacion: ").append(toIndentedString(fechaCreacion)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

