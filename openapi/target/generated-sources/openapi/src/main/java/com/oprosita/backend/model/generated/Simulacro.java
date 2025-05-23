package com.oprosita.backend.model.generated;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Simulacro
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-23T22:26:08.390066400+02:00[Europe/Madrid]", comments = "Generator version: 7.5.0")
public class Simulacro {

  private Integer id;

  private String descripcion;

  private Integer archivoId;

  private Integer grupoId;

  public Simulacro id(Integer id) {
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

  public Simulacro descripcion(String descripcion) {
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

  public Simulacro archivoId(Integer archivoId) {
    this.archivoId = archivoId;
    return this;
  }

  /**
   * Get archivoId
   * @return archivoId
  */
  
  @Schema(name = "archivoId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("archivoId")
  public Integer getArchivoId() {
    return archivoId;
  }

  public void setArchivoId(Integer archivoId) {
    this.archivoId = archivoId;
  }

  public Simulacro grupoId(Integer grupoId) {
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
    Simulacro simulacro = (Simulacro) o;
    return Objects.equals(this.id, simulacro.id) &&
        Objects.equals(this.descripcion, simulacro.descripcion) &&
        Objects.equals(this.archivoId, simulacro.archivoId) &&
        Objects.equals(this.grupoId, simulacro.grupoId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, descripcion, archivoId, grupoId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Simulacro {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    descripcion: ").append(toIndentedString(descripcion)).append("\n");
    sb.append("    archivoId: ").append(toIndentedString(archivoId)).append("\n");
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

