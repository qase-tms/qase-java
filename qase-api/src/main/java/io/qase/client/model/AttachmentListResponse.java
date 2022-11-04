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
 * AttachmentListResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-11-04T01:02:11.281898+03:00[Europe/Moscow]")
public class AttachmentListResponse {
  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private Boolean status;

  public static final String SERIALIZED_NAME_RESULT = "result";
  @SerializedName(SERIALIZED_NAME_RESULT)
  private AttachmentListResponseAllOfResult result;

  public AttachmentListResponse() {
  }

  public AttachmentListResponse status(Boolean status) {
    
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getStatus() {
    return status;
  }


  public void setStatus(Boolean status) {
    this.status = status;
  }


  public AttachmentListResponse result(AttachmentListResponseAllOfResult result) {
    
    this.result = result;
    return this;
  }

   /**
   * Get result
   * @return result
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AttachmentListResponseAllOfResult getResult() {
    return result;
  }


  public void setResult(AttachmentListResponseAllOfResult result) {
    this.result = result;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AttachmentListResponse attachmentListResponse = (AttachmentListResponse) o;
    return Objects.equals(this.status, attachmentListResponse.status) &&
        Objects.equals(this.result, attachmentListResponse.result);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, result);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AttachmentListResponse {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
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
    openapiFields.add("status");
    openapiFields.add("result");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AttachmentListResponse.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AttachmentListResponse' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AttachmentListResponse> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AttachmentListResponse.class));

       return (TypeAdapter<T>) new TypeAdapter<AttachmentListResponse>() {
           @Override
           public void write(JsonWriter out, AttachmentListResponse value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AttachmentListResponse read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of AttachmentListResponse given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AttachmentListResponse
  * @throws IOException if the JSON string is invalid with respect to AttachmentListResponse
  */
  public static AttachmentListResponse fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AttachmentListResponse.class);
  }

 /**
  * Convert an instance of AttachmentListResponse to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

