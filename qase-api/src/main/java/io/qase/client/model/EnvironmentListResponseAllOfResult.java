/*
 * Qase.io API
 * Qase API Specification.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: support@qase.io
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.qase.client.model;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.qase.client.JSON;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * EnvironmentListResponseAllOfResult
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-11-04T01:02:11.281898+03:00[Europe/Moscow]")
public class EnvironmentListResponseAllOfResult {
  public static final String SERIALIZED_NAME_TOTAL = "total";
  @SerializedName(SERIALIZED_NAME_TOTAL)
  private Integer total;

  public static final String SERIALIZED_NAME_FILTERED = "filtered";
  @SerializedName(SERIALIZED_NAME_FILTERED)
  private Integer filtered;

  public static final String SERIALIZED_NAME_COUNT = "count";
  @SerializedName(SERIALIZED_NAME_COUNT)
  private Integer count;

  public static final String SERIALIZED_NAME_ENTITIES = "entities";
  @SerializedName(SERIALIZED_NAME_ENTITIES)
  private List<Environment> entities = null;

  public EnvironmentListResponseAllOfResult() {
  }

  public EnvironmentListResponseAllOfResult total(Integer total) {
    
    this.total = total;
    return this;
  }

   /**
   * Get total
   * @return total
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getTotal() {
    return total;
  }


  public void setTotal(Integer total) {
    this.total = total;
  }


  public EnvironmentListResponseAllOfResult filtered(Integer filtered) {
    
    this.filtered = filtered;
    return this;
  }

   /**
   * Get filtered
   * @return filtered
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getFiltered() {
    return filtered;
  }


  public void setFiltered(Integer filtered) {
    this.filtered = filtered;
  }


  public EnvironmentListResponseAllOfResult count(Integer count) {
    
    this.count = count;
    return this;
  }

   /**
   * Get count
   * @return count
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getCount() {
    return count;
  }


  public void setCount(Integer count) {
    this.count = count;
  }


  public EnvironmentListResponseAllOfResult entities(List<Environment> entities) {
    
    this.entities = entities;
    return this;
  }

  public EnvironmentListResponseAllOfResult addEntitiesItem(Environment entitiesItem) {
    if (this.entities == null) {
      this.entities = new ArrayList<>();
    }
    this.entities.add(entitiesItem);
    return this;
  }

   /**
   * Get entities
   * @return entities
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Environment> getEntities() {
    return entities;
  }


  public void setEntities(List<Environment> entities) {
    this.entities = entities;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EnvironmentListResponseAllOfResult environmentListResponseAllOfResult = (EnvironmentListResponseAllOfResult) o;
    return Objects.equals(this.total, environmentListResponseAllOfResult.total) &&
        Objects.equals(this.filtered, environmentListResponseAllOfResult.filtered) &&
        Objects.equals(this.count, environmentListResponseAllOfResult.count) &&
        Objects.equals(this.entities, environmentListResponseAllOfResult.entities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(total, filtered, count, entities);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EnvironmentListResponseAllOfResult {\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    filtered: ").append(toIndentedString(filtered)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    entities: ").append(toIndentedString(entities)).append("\n");
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


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("total");
    openapiFields.add("filtered");
    openapiFields.add("count");
    openapiFields.add("entities");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!EnvironmentListResponseAllOfResult.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'EnvironmentListResponseAllOfResult' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<EnvironmentListResponseAllOfResult> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(EnvironmentListResponseAllOfResult.class));

       return (TypeAdapter<T>) new TypeAdapter<EnvironmentListResponseAllOfResult>() {
           @Override
           public void write(JsonWriter out, EnvironmentListResponseAllOfResult value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public EnvironmentListResponseAllOfResult read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of EnvironmentListResponseAllOfResult given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of EnvironmentListResponseAllOfResult
  * @throws IOException if the JSON string is invalid with respect to EnvironmentListResponseAllOfResult
  */
  public static EnvironmentListResponseAllOfResult fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, EnvironmentListResponseAllOfResult.class);
  }

 /**
  * Convert an instance of EnvironmentListResponseAllOfResult to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

