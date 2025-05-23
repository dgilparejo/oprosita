package com.oprosita.backend.model.generated;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;
import java.util.NoSuchElementException;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * ContenidoItem
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-05-23T22:26:08.390066400+02:00[Europe/Madrid]", comments = "Generator version: 7.5.0")
public class ContenidoItem {

  private Integer id;

  private String texto;

  /**
   * Gets or Sets tipoContenido
   */
  public enum TipoContenidoEnum {
    TEMAS("temas"),
    
    PROGRAMACION("programacion"),
    
    PRACTICO("practico");

    private String value;

    TipoContenidoEnum(String value) {
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
    public static TipoContenidoEnum fromValue(String value) {
      for (TipoContenidoEnum b : TipoContenidoEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TipoContenidoEnum tipoContenido;

  private JsonNullable<Integer> alumnoId = JsonNullable.<Integer>undefined();

  private Integer grupoId;

  private String mes;

  private JsonNullable<Integer> archivoId = JsonNullable.<Integer>undefined();

  public ContenidoItem id(Integer id) {
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

  public ContenidoItem texto(String texto) {
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

  public ContenidoItem tipoContenido(TipoContenidoEnum tipoContenido) {
    this.tipoContenido = tipoContenido;
    return this;
  }

  /**
   * Get tipoContenido
   * @return tipoContenido
  */
  
  @Schema(name = "tipoContenido", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tipoContenido")
  public TipoContenidoEnum getTipoContenido() {
    return tipoContenido;
  }

  public void setTipoContenido(TipoContenidoEnum tipoContenido) {
    this.tipoContenido = tipoContenido;
  }

  public ContenidoItem alumnoId(Integer alumnoId) {
    this.alumnoId = JsonNullable.of(alumnoId);
    return this;
  }

  /**
   * Get alumnoId
   * @return alumnoId
  */
  
  @Schema(name = "alumnoId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("alumnoId")
  public JsonNullable<Integer> getAlumnoId() {
    return alumnoId;
  }

  public void setAlumnoId(JsonNullable<Integer> alumnoId) {
    this.alumnoId = alumnoId;
  }

  public ContenidoItem grupoId(Integer grupoId) {
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

  public ContenidoItem mes(String mes) {
    this.mes = mes;
    return this;
  }

  /**
   * Get mes
   * @return mes
  */
  
  @Schema(name = "mes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("mes")
  public String getMes() {
    return mes;
  }

  public void setMes(String mes) {
    this.mes = mes;
  }

  public ContenidoItem archivoId(Integer archivoId) {
    this.archivoId = JsonNullable.of(archivoId);
    return this;
  }

  /**
   * Get archivoId
   * @return archivoId
  */
  
  @Schema(name = "archivoId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("archivoId")
  public JsonNullable<Integer> getArchivoId() {
    return archivoId;
  }

  public void setArchivoId(JsonNullable<Integer> archivoId) {
    this.archivoId = archivoId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContenidoItem contenidoItem = (ContenidoItem) o;
    return Objects.equals(this.id, contenidoItem.id) &&
        Objects.equals(this.texto, contenidoItem.texto) &&
        Objects.equals(this.tipoContenido, contenidoItem.tipoContenido) &&
        equalsNullable(this.alumnoId, contenidoItem.alumnoId) &&
        Objects.equals(this.grupoId, contenidoItem.grupoId) &&
        Objects.equals(this.mes, contenidoItem.mes) &&
        equalsNullable(this.archivoId, contenidoItem.archivoId);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, texto, tipoContenido, hashCodeNullable(alumnoId), grupoId, mes, hashCodeNullable(archivoId));
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContenidoItem {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    texto: ").append(toIndentedString(texto)).append("\n");
    sb.append("    tipoContenido: ").append(toIndentedString(tipoContenido)).append("\n");
    sb.append("    alumnoId: ").append(toIndentedString(alumnoId)).append("\n");
    sb.append("    grupoId: ").append(toIndentedString(grupoId)).append("\n");
    sb.append("    mes: ").append(toIndentedString(mes)).append("\n");
    sb.append("    archivoId: ").append(toIndentedString(archivoId)).append("\n");
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

