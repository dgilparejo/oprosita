package com.oprosita.backend.model.generated;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.oprosita.backend.model.generated.Mes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Grupo
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-23T22:26:08.390066400+02:00[Europe/Madrid]", comments = "Generator version: 7.5.0")
public class Grupo {

  private Integer id;

  private String nombre;

  @Valid
  private List<@Valid Mes> meses = new ArrayList<>();

  public Grupo id(Integer id) {
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

  public Grupo nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
  */
  
  @Schema(name = "nombre", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nombre")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Grupo meses(List<@Valid Mes> meses) {
    this.meses = meses;
    return this;
  }

  public Grupo addMesesItem(Mes mesesItem) {
    if (this.meses == null) {
      this.meses = new ArrayList<>();
    }
    this.meses.add(mesesItem);
    return this;
  }

  /**
   * Get meses
   * @return meses
  */
  @Valid 
  @Schema(name = "meses", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("meses")
  public List<@Valid Mes> getMeses() {
    return meses;
  }

  public void setMeses(List<@Valid Mes> meses) {
    this.meses = meses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Grupo grupo = (Grupo) o;
    return Objects.equals(this.id, grupo.id) &&
        Objects.equals(this.nombre, grupo.nombre) &&
        Objects.equals(this.meses, grupo.meses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, meses);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Grupo {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    meses: ").append(toIndentedString(meses)).append("\n");
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

