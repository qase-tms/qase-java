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
import com.google.gson.annotations.JsonAdapter;
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
 * ProjectCreate
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-11-04T01:02:11.281898+03:00[Europe/Moscow]")
public class ProjectCreate {
  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_CODE = "code";
  @SerializedName(SERIALIZED_NAME_CODE)
  private String code;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  /**
   * Gets or Sets access
   */
  @JsonAdapter(AccessEnum.Adapter.class)
  public enum AccessEnum {
    ALL("all"),
    
    GROUP("group"),
    
    NONE("none");

    private String value;

    AccessEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AccessEnum fromValue(String value) {
      for (AccessEnum b : AccessEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<AccessEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AccessEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AccessEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return AccessEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_ACCESS = "access";
  @SerializedName(SERIALIZED_NAME_ACCESS)
  private AccessEnum access;

  public static final String SERIALIZED_NAME_GROUP = "group";
  @SerializedName(SERIALIZED_NAME_GROUP)
  private String group;

  public ProjectCreate() {
  }

  public ProjectCreate title(String title) {
    
    this.title = title;
    return this;
  }

   /**
   * Project title.
   * @return title
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Project title.")

  public String getTitle() {
    return title;
  }


  public void setTitle(String title) {
    this.title = title;
  }


  public ProjectCreate code(String code) {
    
    this.code = code;
    return this;
  }

   /**
   * Project code. Unique for team. Digits and special characters are not allowed.
   * @return code
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Project code. Unique for team. Digits and special characters are not allowed.")

  public String getCode() {
    return code;
  }


  public void setCode(String code) {
    this.code = code;
  }


  public ProjectCreate description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Project description.
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Project description.")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public ProjectCreate access(AccessEnum access) {
    
    this.access = access;
    return this;
  }

   /**
   * Get access
   * @return access
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AccessEnum getAccess() {
    return access;
  }


  public void setAccess(AccessEnum access) {
    this.access = access;
  }


  public ProjectCreate group(String group) {
    
    this.group = group;
    return this;
  }

   /**
   * Team group hash. Required if access param is set to group.
   * @return group
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Team group hash. Required if access param is set to group.")

  public String getGroup() {
    return group;
  }


  public void setGroup(String group) {
    this.group = group;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectCreate projectCreate = (ProjectCreate) o;
    return Objects.equals(this.title, projectCreate.title) &&
        Objects.equals(this.code, projectCreate.code) &&
        Objects.equals(this.description, projectCreate.description) &&
        Objects.equals(this.access, projectCreate.access) &&
        Objects.equals(this.group, projectCreate.group);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, code, description, access, group);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectCreate {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    access: ").append(toIndentedString(access)).append("\n");
    sb.append("    group: ").append(toIndentedString(group)).append("\n");
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
    openapiFields.add("title");
    openapiFields.add("code");
    openapiFields.add("description");
    openapiFields.add("access");
    openapiFields.add("group");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("title");
    openapiRequiredFields.add("code");
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ProjectCreate.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ProjectCreate' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ProjectCreate> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ProjectCreate.class));

       return (TypeAdapter<T>) new TypeAdapter<ProjectCreate>() {
           @Override
           public void write(JsonWriter out, ProjectCreate value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ProjectCreate read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ProjectCreate given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ProjectCreate
  * @throws IOException if the JSON string is invalid with respect to ProjectCreate
  */
  public static ProjectCreate fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ProjectCreate.class);
  }

 /**
  * Convert an instance of ProjectCreate to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

