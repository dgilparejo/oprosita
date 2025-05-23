package com.oprosita.backend.model.generated;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Archivo
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-23T22:26:08.390066400+02:00[Europe/Madrid]", comments = "Generator version: 7.5.0")
public class Archivo {

  private Integer id;

  private String nombre;

  private String tipo;

  private String url;

  private byte[] datos;

  public Archivo id(Integer id) {
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

  public Archivo nombre(String nombre) {
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

  public Archivo tipo(String tipo) {
    this.tipo = tipo;
    return this;
  }

  /**
   * Get tipo
   * @return tipo
  */
  
  @Schema(name = "tipo", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tipo")
  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public Archivo url(String url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
  */
  
  @Schema(name = "url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("url")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Archivo datos(byte[] datos) {
    this.datos = datos;
    return this;
  }

  /**
   * Get datos
   * @return datos
  */
  
  @Schema(name = "datos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("datos")
  public byte[] getDatos() {
    return datos;
  }

  public void setDatos(byte[] datos) {
    this.datos = datos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Archivo archivo = (Archivo) o;
    return Objects.equals(this.id, archivo.id) &&
        Objects.equals(this.nombre, archivo.nombre) &&
        Objects.equals(this.tipo, archivo.tipo) &&
        Objects.equals(this.url, archivo.url) &&
        Arrays.equals(this.datos, archivo.datos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, tipo, url, Arrays.hashCode(datos));
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Archivo {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    datos: ").append(toIndentedString(datos)).append("\n");
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

