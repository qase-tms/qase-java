/*
 * Qase.io TestOps API v1
 * Qase TestOps API v1 Specification.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: support@qase.io
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.qase.client.v1.models;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.qase.client.v1.models.SearchResponseAllOfResultEntities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.qase.client.v1.JSON;

/**
 * SearchResponseAllOfResult
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-10-07T13:46:56.402996677Z[Etc/UTC]", comments = "Generator version: 7.4.0")
public class SearchResponseAllOfResult {
  public static final String SERIALIZED_NAME_ENTITIES = "entities";
  @SerializedName(SERIALIZED_NAME_ENTITIES)
  private List<SearchResponseAllOfResultEntities> entities = new ArrayList<>();

  public static final String SERIALIZED_NAME_TOTAL = "total";
  @SerializedName(SERIALIZED_NAME_TOTAL)
  private Integer total;

  public SearchResponseAllOfResult() {
  }

  public SearchResponseAllOfResult entities(List<SearchResponseAllOfResultEntities> entities) {
    this.entities = entities;
    return this;
  }

  public SearchResponseAllOfResult addEntitiesItem(SearchResponseAllOfResultEntities entitiesItem) {
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
  @javax.annotation.Nonnull
  public List<SearchResponseAllOfResultEntities> getEntities() {
    return entities;
  }

  public void setEntities(List<SearchResponseAllOfResultEntities> entities) {
    this.entities = entities;
  }


  public SearchResponseAllOfResult total(Integer total) {
    this.total = total;
    return this;
  }

   /**
   * Get total
   * @return total
  **/
  @javax.annotation.Nonnull
  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchResponseAllOfResult searchResponseAllOfResult = (SearchResponseAllOfResult) o;
    return Objects.equals(this.entities, searchResponseAllOfResult.entities) &&
        Objects.equals(this.total, searchResponseAllOfResult.total);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entities, total);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchResponseAllOfResult {\n");
    sb.append("    entities: ").append(toIndentedString(entities)).append("\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
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
    openapiFields.add("entities");
    openapiFields.add("total");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("entities");
    openapiRequiredFields.add("total");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to SearchResponseAllOfResult
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!SearchResponseAllOfResult.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in SearchResponseAllOfResult is not found in the empty JSON string", SearchResponseAllOfResult.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!SearchResponseAllOfResult.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `SearchResponseAllOfResult` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : SearchResponseAllOfResult.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // ensure the json data is an array
      if (!jsonObj.get("entities").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `entities` to be an array in the JSON string but got `%s`", jsonObj.get("entities").toString()));
      }

      JsonArray jsonArrayentities = jsonObj.getAsJsonArray("entities");
      // validate the required field `entities` (array)
      for (int i = 0; i < jsonArrayentities.size(); i++) {
        SearchResponseAllOfResultEntities.validateJsonElement(jsonArrayentities.get(i));
      };
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!SearchResponseAllOfResult.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'SearchResponseAllOfResult' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<SearchResponseAllOfResult> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(SearchResponseAllOfResult.class));

       return (TypeAdapter<T>) new TypeAdapter<SearchResponseAllOfResult>() {
           @Override
           public void write(JsonWriter out, SearchResponseAllOfResult value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public SearchResponseAllOfResult read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of SearchResponseAllOfResult given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of SearchResponseAllOfResult
  * @throws IOException if the JSON string is invalid with respect to SearchResponseAllOfResult
  */
  public static SearchResponseAllOfResult fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, SearchResponseAllOfResult.class);
  }

 /**
  * Convert an instance of SearchResponseAllOfResult to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
