package com.oprosita.backend.model.generated;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * Conversacion
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-23T22:26:08.390066400+02:00[Europe/Madrid]", comments = "Generator version: 7.5.0")
public class Conversacion {

  private Integer id;

  private Integer usuarioId;

  private Integer otroUsuarioId;

  private String ultimoMensaje;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime fechaUltimoMensaje;

  private Integer noLeidos;

  public Conversacion id(Integer id) {
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

  public Conversacion usuarioId(Integer usuarioId) {
    this.usuarioId = usuarioId;
    return this;
  }

  /**
   * Get usuarioId
   * @return usuarioId
  */
  
  @Schema(name = "usuarioId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("usuarioId")
  public Integer getUsuarioId() {
    return usuarioId;
  }

  public void setUsuarioId(Integer usuarioId) {
    this.usuarioId = usuarioId;
  }

  public Conversacion otroUsuarioId(Integer otroUsuarioId) {
    this.otroUsuarioId = otroUsuarioId;
    return this;
  }

  /**
   * Get otroUsuarioId
   * @return otroUsuarioId
  */
  
  @Schema(name = "otroUsuarioId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("otroUsuarioId")
  public Integer getOtroUsuarioId() {
    return otroUsuarioId;
  }

  public void setOtroUsuarioId(Integer otroUsuarioId) {
    this.otroUsuarioId = otroUsuarioId;
  }

  public Conversacion ultimoMensaje(String ultimoMensaje) {
    this.ultimoMensaje = ultimoMensaje;
    return this;
  }

  /**
   * Get ultimoMensaje
   * @return ultimoMensaje
  */
  
  @Schema(name = "ultimoMensaje", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ultimoMensaje")
  public String getUltimoMensaje() {
    return ultimoMensaje;
  }

  public void setUltimoMensaje(String ultimoMensaje) {
    this.ultimoMensaje = ultimoMensaje;
  }

  public Conversacion fechaUltimoMensaje(OffsetDateTime fechaUltimoMensaje) {
    this.fechaUltimoMensaje = fechaUltimoMensaje;
    return this;
  }

  /**
   * Get fechaUltimoMensaje
   * @return fechaUltimoMensaje
  */
  @Valid 
  @Schema(name = "fechaUltimoMensaje", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fechaUltimoMensaje")
  public OffsetDateTime getFechaUltimoMensaje() {
    return fechaUltimoMensaje;
  }

  public void setFechaUltimoMensaje(OffsetDateTime fechaUltimoMensaje) {
    this.fechaUltimoMensaje = fechaUltimoMensaje;
  }

  public Conversacion noLeidos(Integer noLeidos) {
    this.noLeidos = noLeidos;
    return this;
  }

  /**
   * Get noLeidos
   * @return noLeidos
  */
  
  @Schema(name = "noLeidos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("noLeidos")
  public Integer getNoLeidos() {
    return noLeidos;
  }

  public void setNoLeidos(Integer noLeidos) {
    this.noLeidos = noLeidos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Conversacion conversacion = (Conversacion) o;
    return Objects.equals(this.id, conversacion.id) &&
        Objects.equals(this.usuarioId, conversacion.usuarioId) &&
        Objects.equals(this.otroUsuarioId, conversacion.otroUsuarioId) &&
        Objects.equals(this.ultimoMensaje, conversacion.ultimoMensaje) &&
        Objects.equals(this.fechaUltimoMensaje, conversacion.fechaUltimoMensaje) &&
        Objects.equals(this.noLeidos, conversacion.noLeidos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, usuarioId, otroUsuarioId, ultimoMensaje, fechaUltimoMensaje, noLeidos);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Conversacion {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    usuarioId: ").append(toIndentedString(usuarioId)).append("\n");
    sb.append("    otroUsuarioId: ").append(toIndentedString(otroUsuarioId)).append("\n");
    sb.append("    ultimoMensaje: ").append(toIndentedString(ultimoMensaje)).append("\n");
    sb.append("    fechaUltimoMensaje: ").append(toIndentedString(fechaUltimoMensaje)).append("\n");
    sb.append("    noLeidos: ").append(toIndentedString(noLeidos)).append("\n");
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

