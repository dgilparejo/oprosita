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
 * Reunion
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-23T22:26:08.390066400+02:00[Europe/Madrid]", comments = "Generator version: 7.5.0")
public class Reunion {

  private Integer id;

  private String titulo;

  private String descripcion;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime fechaHora;

  private String enlace;

  private Integer grupoId;

  public Reunion id(Integer id) {
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

  public Reunion titulo(String titulo) {
    this.titulo = titulo;
    return this;
  }

  /**
   * Get titulo
   * @return titulo
  */
  
  @Schema(name = "titulo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("titulo")
  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Reunion descripcion(String descripcion) {
    this.descripcion = descripcion;
    return this;
  }

  /**
   * Get descripcion
   * @return descripcion
  */
  
  @Schema(name = "descripcion", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("descripcion")
  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Reunion fechaHora(OffsetDateTime fechaHora) {
    this.fechaHora = fechaHora;
    return this;
  }

  /**
   * Get fechaHora
   * @return fechaHora
  */
  @Valid 
  @Schema(name = "fechaHora", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fechaHora")
  public OffsetDateTime getFechaHora() {
    return fechaHora;
  }

  public void setFechaHora(OffsetDateTime fechaHora) {
    this.fechaHora = fechaHora;
  }

  public Reunion enlace(String enlace) {
    this.enlace = enlace;
    return this;
  }

  /**
   * Get enlace
   * @return enlace
  */
  
  @Schema(name = "enlace", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("enlace")
  public String getEnlace() {
    return enlace;
  }

  public void setEnlace(String enlace) {
    this.enlace = enlace;
  }

  public Reunion grupoId(Integer grupoId) {
    this.grupoId = grupoId;
    return this;
  }

  /**
   * Get grupoId
   * @return grupoId
  */
  
  @Schema(name = "grupoId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("grupoId")
  public Integer getGrupoId() {
    return grupoId;
  }

  public void setGrupoId(Integer grupoId) {
    this.grupoId = grupoId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Reunion reunion = (Reunion) o;
    return Objects.equals(this.id, reunion.id) &&
        Objects.equals(this.titulo, reunion.titulo) &&
        Objects.equals(this.descripcion, reunion.descripcion) &&
        Objects.equals(this.fechaHora, reunion.fechaHora) &&
        Objects.equals(this.enlace, reunion.enlace) &&
        Objects.equals(this.grupoId, reunion.grupoId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, titulo, descripcion, fechaHora, enlace, grupoId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Reunion {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    titulo: ").append(toIndentedString(titulo)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
    sb.append("    fechaHora: ").append(toIndentedString(fechaHora)).append("\n");
    sb.append("    enlace: ").append(toIndentedString(enlace)).append("\n");
    sb.append("    grupoId: ").append(toIndentedString(grupoId)).append("\n");
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

