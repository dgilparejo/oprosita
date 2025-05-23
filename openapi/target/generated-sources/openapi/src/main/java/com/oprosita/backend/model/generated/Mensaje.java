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
 * Mensaje
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-23T22:26:08.390066400+02:00[Europe/Madrid]", comments = "Generator version: 7.5.0")
public class Mensaje {

  private Integer id;

  private Integer remitente;

  private Integer destinatario;

  private String contenido;

  private Boolean leido;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime fechaEnvio;

  public Mensaje id(Integer id) {
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

  public Mensaje remitente(Integer remitente) {
    this.remitente = remitente;
    return this;
  }

  /**
   * ID del usuario remitente
   * @return remitente
  */
  
  @Schema(name = "remitente", description = "ID del usuario remitente", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("remitente")
  public Integer getRemitente() {
    return remitente;
  }

  public void setRemitente(Integer remitente) {
    this.remitente = remitente;
  }

  public Mensaje destinatario(Integer destinatario) {
    this.destinatario = destinatario;
    return this;
  }

  /**
   * ID del usuario destinatario
   * @return destinatario
  */
  
  @Schema(name = "destinatario", description = "ID del usuario destinatario", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("destinatario")
  public Integer getDestinatario() {
    return destinatario;
  }

  public void setDestinatario(Integer destinatario) {
    this.destinatario = destinatario;
  }

  public Mensaje contenido(String contenido) {
    this.contenido = contenido;
    return this;
  }

  /**
   * Get contenido
   * @return contenido
  */
  
  @Schema(name = "contenido", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("contenido")
  public String getContenido() {
    return contenido;
  }

  public void setContenido(String contenido) {
    this.contenido = contenido;
  }

  public Mensaje leido(Boolean leido) {
    this.leido = leido;
    return this;
  }

  /**
   * Get leido
   * @return leido
  */
  
  @Schema(name = "leido", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("leido")
  public Boolean getLeido() {
    return leido;
  }

  public void setLeido(Boolean leido) {
    this.leido = leido;
  }

  public Mensaje fechaEnvio(OffsetDateTime fechaEnvio) {
    this.fechaEnvio = fechaEnvio;
    return this;
  }

  /**
   * Get fechaEnvio
   * @return fechaEnvio
  */
  @Valid 
  @Schema(name = "fechaEnvio", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fechaEnvio")
  public OffsetDateTime getFechaEnvio() {
    return fechaEnvio;
  }

  public void setFechaEnvio(OffsetDateTime fechaEnvio) {
    this.fechaEnvio = fechaEnvio;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Mensaje mensaje = (Mensaje) o;
    return Objects.equals(this.id, mensaje.id) &&
        Objects.equals(this.remitente, mensaje.remitente) &&
        Objects.equals(this.destinatario, mensaje.destinatario) &&
        Objects.equals(this.contenido, mensaje.contenido) &&
        Objects.equals(this.leido, mensaje.leido) &&
        Objects.equals(this.fechaEnvio, mensaje.fechaEnvio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, remitente, destinatario, contenido, leido, fechaEnvio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Mensaje {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    remitente: ").append(toIndentedString(remitente)).append("\n");
    sb.append("    destinatario: ").append(toIndentedString(destinatario)).append("\n");
    sb.append("    contenido: ").append(toIndentedString(contenido)).append("\n");
    sb.append("    leido: ").append(toIndentedString(leido)).append("\n");
    sb.append("    fechaEnvio: ").append(toIndentedString(fechaEnvio)).append("\n");
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

