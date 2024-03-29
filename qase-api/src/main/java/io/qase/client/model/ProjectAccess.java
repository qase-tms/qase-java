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
 * ProjectAccess
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-11-04T01:02:11.281898+03:00[Europe/Moscow]")
public class ProjectAccess {
  public static final String SERIALIZED_NAME_MEMBER_ID = "member_id";
  @SerializedName(SERIALIZED_NAME_MEMBER_ID)
  private Long memberId;

  public ProjectAccess() {
  }

  public ProjectAccess memberId(Long memberId) {
    
    this.memberId = memberId;
    return this;
  }

   /**
   * Team member id title.
   * @return memberId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Team member id title.")

  public Long getMemberId() {
    return memberId;
  }


  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectAccess projectAccess = (ProjectAccess) o;
    return Objects.equals(this.memberId, projectAccess.memberId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(memberId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectAccess {\n");
    sb.append("    memberId: ").append(toIndentedString(memberId)).append("\n");
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
    openapiFields.add("member_id");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ProjectAccess.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ProjectAccess' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ProjectAccess> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ProjectAccess.class));

       return (TypeAdapter<T>) new TypeAdapter<ProjectAccess>() {
           @Override
           public void write(JsonWriter out, ProjectAccess value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ProjectAccess read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ProjectAccess given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ProjectAccess
  * @throws IOException if the JSON string is invalid with respect to ProjectAccess
  */
  public static ProjectAccess fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ProjectAccess.class);
  }

 /**
  * Convert an instance of ProjectAccess to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

