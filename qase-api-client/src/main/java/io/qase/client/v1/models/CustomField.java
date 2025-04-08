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
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;

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
 * CustomField
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class CustomField {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Long id;

  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_ENTITY = "entity";
  @SerializedName(SERIALIZED_NAME_ENTITY)
  private String entity;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private String type;

  public static final String SERIALIZED_NAME_PLACEHOLDER = "placeholder";
  @SerializedName(SERIALIZED_NAME_PLACEHOLDER)
  private String placeholder;

  public static final String SERIALIZED_NAME_DEFAULT_VALUE = "default_value";
  @SerializedName(SERIALIZED_NAME_DEFAULT_VALUE)
  private String defaultValue;

  public static final String SERIALIZED_NAME_VALUE = "value";
  @SerializedName(SERIALIZED_NAME_VALUE)
  private String value;

  public static final String SERIALIZED_NAME_IS_REQUIRED = "is_required";
  @SerializedName(SERIALIZED_NAME_IS_REQUIRED)
  private Boolean isRequired;

  public static final String SERIALIZED_NAME_IS_VISIBLE = "is_visible";
  @SerializedName(SERIALIZED_NAME_IS_VISIBLE)
  private Boolean isVisible;

  public static final String SERIALIZED_NAME_IS_FILTERABLE = "is_filterable";
  @SerializedName(SERIALIZED_NAME_IS_FILTERABLE)
  private Boolean isFilterable;

  public static final String SERIALIZED_NAME_IS_ENABLED_FOR_ALL_PROJECTS = "is_enabled_for_all_projects";
  @SerializedName(SERIALIZED_NAME_IS_ENABLED_FOR_ALL_PROJECTS)
  private Boolean isEnabledForAllProjects;

  public static final String SERIALIZED_NAME_CREATED = "created";
  @Deprecated
  @SerializedName(SERIALIZED_NAME_CREATED)
  private String created;

  public static final String SERIALIZED_NAME_UPDATED = "updated";
  @Deprecated
  @SerializedName(SERIALIZED_NAME_UPDATED)
  private String updated;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private OffsetDateTime updatedAt;

  public static final String SERIALIZED_NAME_PROJECTS_CODES = "projects_codes";
  @SerializedName(SERIALIZED_NAME_PROJECTS_CODES)
  private List<String> projectsCodes;

  public CustomField() {
  }

  public CustomField id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public CustomField title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @javax.annotation.Nullable
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public CustomField entity(String entity) {
    this.entity = entity;
    return this;
  }

   /**
   * Get entity
   * @return entity
  **/
  @javax.annotation.Nullable
  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }


  public CustomField type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nullable
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public CustomField placeholder(String placeholder) {
    this.placeholder = placeholder;
    return this;
  }

   /**
   * Get placeholder
   * @return placeholder
  **/
  @javax.annotation.Nullable
  public String getPlaceholder() {
    return placeholder;
  }

  public void setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
  }


  public CustomField defaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

   /**
   * Get defaultValue
   * @return defaultValue
  **/
  @javax.annotation.Nullable
  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }


  public CustomField value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @javax.annotation.Nullable
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  public CustomField isRequired(Boolean isRequired) {
    this.isRequired = isRequired;
    return this;
  }

   /**
   * Get isRequired
   * @return isRequired
  **/
  @javax.annotation.Nullable
  public Boolean getIsRequired() {
    return isRequired;
  }

  public void setIsRequired(Boolean isRequired) {
    this.isRequired = isRequired;
  }


  public CustomField isVisible(Boolean isVisible) {
    this.isVisible = isVisible;
    return this;
  }

   /**
   * Get isVisible
   * @return isVisible
  **/
  @javax.annotation.Nullable
  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }


  public CustomField isFilterable(Boolean isFilterable) {
    this.isFilterable = isFilterable;
    return this;
  }

   /**
   * Get isFilterable
   * @return isFilterable
  **/
  @javax.annotation.Nullable
  public Boolean getIsFilterable() {
    return isFilterable;
  }

  public void setIsFilterable(Boolean isFilterable) {
    this.isFilterable = isFilterable;
  }


  public CustomField isEnabledForAllProjects(Boolean isEnabledForAllProjects) {
    this.isEnabledForAllProjects = isEnabledForAllProjects;
    return this;
  }

   /**
   * Get isEnabledForAllProjects
   * @return isEnabledForAllProjects
  **/
  @javax.annotation.Nullable
  public Boolean getIsEnabledForAllProjects() {
    return isEnabledForAllProjects;
  }

  public void setIsEnabledForAllProjects(Boolean isEnabledForAllProjects) {
    this.isEnabledForAllProjects = isEnabledForAllProjects;
  }


  @Deprecated
  public CustomField created(String created) {
    this.created = created;
    return this;
  }

   /**
   * Deprecated, use the &#x60;created_at&#x60; property instead.
   * @return created
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  public String getCreated() {
    return created;
  }

  @Deprecated
  public void setCreated(String created) {
    this.created = created;
  }


  @Deprecated
  public CustomField updated(String updated) {
    this.updated = updated;
    return this;
  }

   /**
   * Deprecated, use the &#x60;updated_at&#x60; property instead.
   * @return updated
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  public String getUpdated() {
    return updated;
  }

  @Deprecated
  public void setUpdated(String updated) {
    this.updated = updated;
  }


  public CustomField createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Get createdAt
   * @return createdAt
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }


  public CustomField updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Get updatedAt
   * @return updatedAt
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }


  public CustomField projectsCodes(List<String> projectsCodes) {
    this.projectsCodes = projectsCodes;
    return this;
  }

  public CustomField addProjectsCodesItem(String projectsCodesItem) {
    if (this.projectsCodes == null) {
      this.projectsCodes = new ArrayList<>();
    }
    this.projectsCodes.add(projectsCodesItem);
    return this;
  }

   /**
   * Get projectsCodes
   * @return projectsCodes
  **/
  @javax.annotation.Nullable
  public List<String> getProjectsCodes() {
    return projectsCodes;
  }

  public void setProjectsCodes(List<String> projectsCodes) {
    this.projectsCodes = projectsCodes;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomField customField = (CustomField) o;
    return Objects.equals(this.id, customField.id) &&
        Objects.equals(this.title, customField.title) &&
        Objects.equals(this.entity, customField.entity) &&
        Objects.equals(this.type, customField.type) &&
        Objects.equals(this.placeholder, customField.placeholder) &&
        Objects.equals(this.defaultValue, customField.defaultValue) &&
        Objects.equals(this.value, customField.value) &&
        Objects.equals(this.isRequired, customField.isRequired) &&
        Objects.equals(this.isVisible, customField.isVisible) &&
        Objects.equals(this.isFilterable, customField.isFilterable) &&
        Objects.equals(this.isEnabledForAllProjects, customField.isEnabledForAllProjects) &&
        Objects.equals(this.created, customField.created) &&
        Objects.equals(this.updated, customField.updated) &&
        Objects.equals(this.createdAt, customField.createdAt) &&
        Objects.equals(this.updatedAt, customField.updatedAt) &&
        Objects.equals(this.projectsCodes, customField.projectsCodes);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, entity, type, placeholder, defaultValue, value, isRequired, isVisible, isFilterable, isEnabledForAllProjects, created, updated, createdAt, updatedAt, projectsCodes);
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
    sb.append("class CustomField {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    entity: ").append(toIndentedString(entity)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    placeholder: ").append(toIndentedString(placeholder)).append("\n");
    sb.append("    defaultValue: ").append(toIndentedString(defaultValue)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    isRequired: ").append(toIndentedString(isRequired)).append("\n");
    sb.append("    isVisible: ").append(toIndentedString(isVisible)).append("\n");
    sb.append("    isFilterable: ").append(toIndentedString(isFilterable)).append("\n");
    sb.append("    isEnabledForAllProjects: ").append(toIndentedString(isEnabledForAllProjects)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    updated: ").append(toIndentedString(updated)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    projectsCodes: ").append(toIndentedString(projectsCodes)).append("\n");
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
    openapiFields.add("id");
    openapiFields.add("title");
    openapiFields.add("entity");
    openapiFields.add("type");
    openapiFields.add("placeholder");
    openapiFields.add("default_value");
    openapiFields.add("value");
    openapiFields.add("is_required");
    openapiFields.add("is_visible");
    openapiFields.add("is_filterable");
    openapiFields.add("is_enabled_for_all_projects");
    openapiFields.add("created");
    openapiFields.add("updated");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");
    openapiFields.add("projects_codes");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to CustomField
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!CustomField.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in CustomField is not found in the empty JSON string", CustomField.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!CustomField.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CustomField` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("title") != null && !jsonObj.get("title").isJsonNull()) && !jsonObj.get("title").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj.get("title").toString()));
      }
      if ((jsonObj.get("entity") != null && !jsonObj.get("entity").isJsonNull()) && !jsonObj.get("entity").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `entity` to be a primitive type in the JSON string but got `%s`", jsonObj.get("entity").toString()));
      }
      if ((jsonObj.get("type") != null && !jsonObj.get("type").isJsonNull()) && !jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      if ((jsonObj.get("placeholder") != null && !jsonObj.get("placeholder").isJsonNull()) && !jsonObj.get("placeholder").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `placeholder` to be a primitive type in the JSON string but got `%s`", jsonObj.get("placeholder").toString()));
      }
      if ((jsonObj.get("default_value") != null && !jsonObj.get("default_value").isJsonNull()) && !jsonObj.get("default_value").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `default_value` to be a primitive type in the JSON string but got `%s`", jsonObj.get("default_value").toString()));
      }
      if ((jsonObj.get("value") != null && !jsonObj.get("value").isJsonNull()) && !jsonObj.get("value").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `value` to be a primitive type in the JSON string but got `%s`", jsonObj.get("value").toString()));
      }
      if ((jsonObj.get("created") != null && !jsonObj.get("created").isJsonNull()) && !jsonObj.get("created").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `created` to be a primitive type in the JSON string but got `%s`", jsonObj.get("created").toString()));
      }
      if ((jsonObj.get("updated") != null && !jsonObj.get("updated").isJsonNull()) && !jsonObj.get("updated").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `updated` to be a primitive type in the JSON string but got `%s`", jsonObj.get("updated").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("projects_codes") != null && !jsonObj.get("projects_codes").isJsonNull() && !jsonObj.get("projects_codes").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `projects_codes` to be an array in the JSON string but got `%s`", jsonObj.get("projects_codes").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CustomField.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CustomField' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CustomField> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CustomField.class));

       return (TypeAdapter<T>) new TypeAdapter<CustomField>() {
           @Override
           public void write(JsonWriter out, CustomField value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CustomField read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CustomField given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CustomField
  * @throws IOException if the JSON string is invalid with respect to CustomField
  */
  public static CustomField fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CustomField.class);
  }

 /**
  * Convert an instance of CustomField to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

