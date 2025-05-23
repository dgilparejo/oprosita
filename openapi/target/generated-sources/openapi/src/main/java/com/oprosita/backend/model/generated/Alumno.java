package com.oprosita.backend.model.generated;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import com.oprosita.backend.model.generated.Usuario;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Alumno
 */


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-23T22:26:08.390066400+02:00[Europe/Madrid]", comments = "Generator version: 7.5.0")
public class Alumno extends Usuario {

  /**
   * Gets or Sets tipo
   */
  public enum TipoEnum {
    ALUMNO("alumno");

    private String value;

    TipoEnum(String value) {
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
    public static TipoEnum fromValue(String value) {
      for (TipoEnum b : TipoEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TipoEnum tipo;

  public Alumno tipo(TipoEnum tipo) {
    this.tipo = tipo;
    return this;
  }

  /**
   * Get tipo
   * @return tipo
  */
  
  @Schema(name = "tipo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tipo")
  public TipoEnum getTipo() {
    return tipo;
  }

  public void setTipo(TipoEnum tipo) {
    this.tipo = tipo;
  }


  public Alumno id(Integer id) {
    super.id(id);
    return this;
  }

  public Alumno nombre(String nombre) {
    super.nombre(nombre);
    return this;
  }

  public Alumno grupoId(Integer grupoId) {
    super.grupoId(grupoId);
    return this;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Alumno alumno = (Alumno) o;
    return Objects.equals(this.tipo, alumno.tipo) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tipo, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Alumno {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
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

