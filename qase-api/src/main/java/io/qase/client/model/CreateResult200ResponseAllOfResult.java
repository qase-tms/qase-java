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
 * CreateResult200ResponseAllOfResult
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-11-04T01:02:11.281898+03:00[Europe/Moscow]")
public class CreateResult200ResponseAllOfResult {
  public static final String SERIALIZED_NAME_CASE_ID = "case_id";
  @SerializedName(SERIALIZED_NAME_CASE_ID)
  private Long caseId;

  public static final String SERIALIZED_NAME_HASH = "hash";
  @SerializedName(SERIALIZED_NAME_HASH)
  private String hash;

  public CreateResult200ResponseAllOfResult() {
  }

  public CreateResult200ResponseAllOfResult caseId(Long caseId) {
    
    this.caseId = caseId;
    return this;
  }

   /**
   * Get caseId
   * @return caseId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getCaseId() {
    return caseId;
  }


  public void setCaseId(Long caseId) {
    this.caseId = caseId;
  }


  public CreateResult200ResponseAllOfResult hash(String hash) {
    
    this.hash = hash;
    return this;
  }

   /**
   * Get hash
   * @return hash
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getHash() {
    return hash;
  }


  public void setHash(String hash) {
    this.hash = hash;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateResult200ResponseAllOfResult createResult200ResponseAllOfResult = (CreateResult200ResponseAllOfResult) o;
    return Objects.equals(this.caseId, createResult200ResponseAllOfResult.caseId) &&
        Objects.equals(this.hash, createResult200ResponseAllOfResult.hash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(caseId, hash);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateResult200ResponseAllOfResult {\n");
    sb.append("    caseId: ").append(toIndentedString(caseId)).append("\n");
    sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
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
    openapiFields.add("case_id");
    openapiFields.add("hash");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CreateResult200ResponseAllOfResult.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CreateResult200ResponseAllOfResult' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CreateResult200ResponseAllOfResult> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CreateResult200ResponseAllOfResult.class));

       return (TypeAdapter<T>) new TypeAdapter<CreateResult200ResponseAllOfResult>() {
           @Override
           public void write(JsonWriter out, CreateResult200ResponseAllOfResult value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CreateResult200ResponseAllOfResult read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CreateResult200ResponseAllOfResult given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CreateResult200ResponseAllOfResult
  * @throws IOException if the JSON string is invalid with respect to CreateResult200ResponseAllOfResult
  */
  public static CreateResult200ResponseAllOfResult fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CreateResult200ResponseAllOfResult.class);
  }

 /**
  * Convert an instance of CreateResult200ResponseAllOfResult to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

