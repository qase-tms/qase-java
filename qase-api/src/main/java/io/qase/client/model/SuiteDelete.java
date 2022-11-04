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
import java.util.HashSet;
import java.util.Objects;

/**
 * SuiteDelete
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-11-04T01:02:11.281898+03:00[Europe/Moscow]")
public class SuiteDelete {
  public static final String SERIALIZED_NAME_DESTINATION_ID = "destination_id";
  @SerializedName(SERIALIZED_NAME_DESTINATION_ID)
  private Long destinationId;

  public SuiteDelete() {
  }

  public SuiteDelete destinationId(Long destinationId) {
    
    this.destinationId = destinationId;
    return this;
  }

   /**
   * If provided, child test cases would be moved to suite with such ID.
   * @return destinationId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "If provided, child test cases would be moved to suite with such ID.")

  public Long getDestinationId() {
    return destinationId;
  }


  public void setDestinationId(Long destinationId) {
    this.destinationId = destinationId;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SuiteDelete suiteDelete = (SuiteDelete) o;
    return Objects.equals(this.destinationId, suiteDelete.destinationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(destinationId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SuiteDelete {\n");
    sb.append("    destinationId: ").append(toIndentedString(destinationId)).append("\n");
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
    openapiFields.add("destination_id");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!SuiteDelete.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'SuiteDelete' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<SuiteDelete> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(SuiteDelete.class));

       return (TypeAdapter<T>) new TypeAdapter<SuiteDelete>() {
           @Override
           public void write(JsonWriter out, SuiteDelete value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public SuiteDelete read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of SuiteDelete given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of SuiteDelete
  * @throws IOException if the JSON string is invalid with respect to SuiteDelete
  */
  public static SuiteDelete fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, SuiteDelete.class);
  }

 /**
  * Convert an instance of SuiteDelete to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

