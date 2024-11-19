/*
 * Qase.io TestOps API v2
 * Qase TestOps API v2 Specification.
 *
 * The version of the OpenAPI document: 2.0.0
 * Contact: support@qase.io
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.qase.client.v2.models;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.qase.client.v2.models.BaseErrorFieldResponseErrorFieldsInner;
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

import io.qase.client.v2.JSON;

/**
 * CreateResultV2422Response
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-10-29T12:24:05.813233483Z[Etc/UTC]", comments = "Generator version: 7.4.0")
public class CreateResultV2422Response {
  public static final String SERIALIZED_NAME_ERROR_MESSAGE = "errorMessage";
  @SerializedName(SERIALIZED_NAME_ERROR_MESSAGE)
  private String errorMessage;

  public static final String SERIALIZED_NAME_ERROR_FIELDS = "errorFields";
  @SerializedName(SERIALIZED_NAME_ERROR_FIELDS)
  private List<BaseErrorFieldResponseErrorFieldsInner> errorFields;

  public CreateResultV2422Response() {
  }

  public CreateResultV2422Response errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

   /**
   * Get errorMessage
   * @return errorMessage
  **/
  @javax.annotation.Nullable
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }


  public CreateResultV2422Response errorFields(List<BaseErrorFieldResponseErrorFieldsInner> errorFields) {
    this.errorFields = errorFields;
    return this;
  }

  public CreateResultV2422Response addErrorFieldsItem(BaseErrorFieldResponseErrorFieldsInner errorFieldsItem) {
    if (this.errorFields == null) {
      this.errorFields = new ArrayList<>();
    }
    this.errorFields.add(errorFieldsItem);
    return this;
  }

   /**
   * Get errorFields
   * @return errorFields
  **/
  @javax.annotation.Nullable
  public List<BaseErrorFieldResponseErrorFieldsInner> getErrorFields() {
    return errorFields;
  }

  public void setErrorFields(List<BaseErrorFieldResponseErrorFieldsInner> errorFields) {
    this.errorFields = errorFields;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateResultV2422Response createResultV2422Response = (CreateResultV2422Response) o;
    return Objects.equals(this.errorMessage, createResultV2422Response.errorMessage) &&
        Objects.equals(this.errorFields, createResultV2422Response.errorFields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorMessage, errorFields);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateResultV2422Response {\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    errorFields: ").append(toIndentedString(errorFields)).append("\n");
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
    openapiFields.add("errorMessage");
    openapiFields.add("errorFields");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to CreateResultV2422Response
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!CreateResultV2422Response.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in CreateResultV2422Response is not found in the empty JSON string", CreateResultV2422Response.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!CreateResultV2422Response.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CreateResultV2422Response` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("errorMessage") != null && !jsonObj.get("errorMessage").isJsonNull()) && !jsonObj.get("errorMessage").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `errorMessage` to be a primitive type in the JSON string but got `%s`", jsonObj.get("errorMessage").toString()));
      }
      if (jsonObj.get("errorFields") != null && !jsonObj.get("errorFields").isJsonNull()) {
        JsonArray jsonArrayerrorFields = jsonObj.getAsJsonArray("errorFields");
        if (jsonArrayerrorFields != null) {
          // ensure the json data is an array
          if (!jsonObj.get("errorFields").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `errorFields` to be an array in the JSON string but got `%s`", jsonObj.get("errorFields").toString()));
          }

          // validate the optional field `errorFields` (array)
          for (int i = 0; i < jsonArrayerrorFields.size(); i++) {
            BaseErrorFieldResponseErrorFieldsInner.validateJsonElement(jsonArrayerrorFields.get(i));
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CreateResultV2422Response.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CreateResultV2422Response' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CreateResultV2422Response> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CreateResultV2422Response.class));

       return (TypeAdapter<T>) new TypeAdapter<CreateResultV2422Response>() {
           @Override
           public void write(JsonWriter out, CreateResultV2422Response value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CreateResultV2422Response read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CreateResultV2422Response given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CreateResultV2422Response
  * @throws IOException if the JSON string is invalid with respect to CreateResultV2422Response
  */
  public static CreateResultV2422Response fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CreateResultV2422Response.class);
  }

 /**
  * Convert an instance of CreateResultV2422Response to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
