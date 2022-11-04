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
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.IOException;
import java.util.*;

/**
 * CustomFieldCreate
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-11-04T01:02:11.281898+03:00[Europe/Moscow]")
public class CustomFieldCreate {
  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_VALUE = "value";
  @SerializedName(SERIALIZED_NAME_VALUE)
  private List<CustomFieldCreateValueInner> value = null;

  public static final String SERIALIZED_NAME_ENTITY = "entity";
  @SerializedName(SERIALIZED_NAME_ENTITY)
  private Integer entity;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private Integer type;

  public static final String SERIALIZED_NAME_PLACEHOLDER = "placeholder";
  @SerializedName(SERIALIZED_NAME_PLACEHOLDER)
  private String placeholder;

  public static final String SERIALIZED_NAME_DEFAULT_VALUE = "default_value";
  @SerializedName(SERIALIZED_NAME_DEFAULT_VALUE)
  private String defaultValue;

  public static final String SERIALIZED_NAME_IS_FILTERABLE = "is_filterable";
  @SerializedName(SERIALIZED_NAME_IS_FILTERABLE)
  private Boolean isFilterable;

  public static final String SERIALIZED_NAME_IS_VISIBLE = "is_visible";
  @SerializedName(SERIALIZED_NAME_IS_VISIBLE)
  private Boolean isVisible;

  public static final String SERIALIZED_NAME_IS_REQUIRED = "is_required";
  @SerializedName(SERIALIZED_NAME_IS_REQUIRED)
  private Boolean isRequired;

  public static final String SERIALIZED_NAME_IS_ENABLED_FOR_ALL_PROJECTS = "is_enabled_for_all_projects";
  @SerializedName(SERIALIZED_NAME_IS_ENABLED_FOR_ALL_PROJECTS)
  private Boolean isEnabledForAllProjects;

  public static final String SERIALIZED_NAME_PROJECTS_CODES = "projects_codes";
  @SerializedName(SERIALIZED_NAME_PROJECTS_CODES)
  private List<String> projectsCodes = null;

  public CustomFieldCreate() {
  }

  public CustomFieldCreate title(String title) {
    
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getTitle() {
    return title;
  }


  public void setTitle(String title) {
    this.title = title;
  }


  public CustomFieldCreate value(List<CustomFieldCreateValueInner> value) {
    
    this.value = value;
    return this;
  }

  public CustomFieldCreate addValueItem(CustomFieldCreateValueInner valueItem) {
    if (this.value == null) {
      this.value = new ArrayList<>();
    }
    this.value.add(valueItem);
    return this;
  }

   /**
   * Required if type one of: 3 - selectbox; 5 - radio; 6 - multiselect; 
   * @return value
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Required if type one of: 3 - selectbox; 5 - radio; 6 - multiselect; ")

  public List<CustomFieldCreateValueInner> getValue() {
    return value;
  }


  public void setValue(List<CustomFieldCreateValueInner> value) {
    this.value = value;
  }


  public CustomFieldCreate entity(Integer entity) {
    
    this.entity = entity;
    return this;
  }

   /**
   * Possible values: 0 - case; 1 - run; 2 - defect; 
   * minimum: 0
   * maximum: 2
   * @return entity
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Possible values: 0 - case; 1 - run; 2 - defect; ")

  public Integer getEntity() {
    return entity;
  }


  public void setEntity(Integer entity) {
    this.entity = entity;
  }


  public CustomFieldCreate type(Integer type) {
    
    this.type = type;
    return this;
  }

   /**
   * Possible values: 0 - number; 1 - string; 2 - text; 3 - selectbox; 4 - checkbox; 5 - radio; 6 - multiselect; 7 - url; 8 - user; 9 - datetime; 
   * minimum: 0
   * maximum: 9
   * @return type
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "Possible values: 0 - number; 1 - string; 2 - text; 3 - selectbox; 4 - checkbox; 5 - radio; 6 - multiselect; 7 - url; 8 - user; 9 - datetime; ")

  public Integer getType() {
    return type;
  }


  public void setType(Integer type) {
    this.type = type;
  }


  public CustomFieldCreate placeholder(String placeholder) {
    
    this.placeholder = placeholder;
    return this;
  }

   /**
   * Get placeholder
   * @return placeholder
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPlaceholder() {
    return placeholder;
  }


  public void setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
  }


  public CustomFieldCreate defaultValue(String defaultValue) {
    
    this.defaultValue = defaultValue;
    return this;
  }

   /**
   * Get defaultValue
   * @return defaultValue
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDefaultValue() {
    return defaultValue;
  }


  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }


  public CustomFieldCreate isFilterable(Boolean isFilterable) {
    
    this.isFilterable = isFilterable;
    return this;
  }

   /**
   * Get isFilterable
   * @return isFilterable
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getIsFilterable() {
    return isFilterable;
  }


  public void setIsFilterable(Boolean isFilterable) {
    this.isFilterable = isFilterable;
  }


  public CustomFieldCreate isVisible(Boolean isVisible) {
    
    this.isVisible = isVisible;
    return this;
  }

   /**
   * Get isVisible
   * @return isVisible
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getIsVisible() {
    return isVisible;
  }


  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }


  public CustomFieldCreate isRequired(Boolean isRequired) {
    
    this.isRequired = isRequired;
    return this;
  }

   /**
   * Get isRequired
   * @return isRequired
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getIsRequired() {
    return isRequired;
  }


  public void setIsRequired(Boolean isRequired) {
    this.isRequired = isRequired;
  }


  public CustomFieldCreate isEnabledForAllProjects(Boolean isEnabledForAllProjects) {
    
    this.isEnabledForAllProjects = isEnabledForAllProjects;
    return this;
  }

   /**
   * Get isEnabledForAllProjects
   * @return isEnabledForAllProjects
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getIsEnabledForAllProjects() {
    return isEnabledForAllProjects;
  }


  public void setIsEnabledForAllProjects(Boolean isEnabledForAllProjects) {
    this.isEnabledForAllProjects = isEnabledForAllProjects;
  }


  public CustomFieldCreate projectsCodes(List<String> projectsCodes) {
    
    this.projectsCodes = projectsCodes;
    return this;
  }

  public CustomFieldCreate addProjectsCodesItem(String projectsCodesItem) {
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
  @ApiModelProperty(value = "")

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
    CustomFieldCreate customFieldCreate = (CustomFieldCreate) o;
    return Objects.equals(this.title, customFieldCreate.title) &&
        Objects.equals(this.value, customFieldCreate.value) &&
        Objects.equals(this.entity, customFieldCreate.entity) &&
        Objects.equals(this.type, customFieldCreate.type) &&
        Objects.equals(this.placeholder, customFieldCreate.placeholder) &&
        Objects.equals(this.defaultValue, customFieldCreate.defaultValue) &&
        Objects.equals(this.isFilterable, customFieldCreate.isFilterable) &&
        Objects.equals(this.isVisible, customFieldCreate.isVisible) &&
        Objects.equals(this.isRequired, customFieldCreate.isRequired) &&
        Objects.equals(this.isEnabledForAllProjects, customFieldCreate.isEnabledForAllProjects) &&
        Objects.equals(this.projectsCodes, customFieldCreate.projectsCodes);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, value, entity, type, placeholder, defaultValue, isFilterable, isVisible, isRequired, isEnabledForAllProjects, projectsCodes);
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
    sb.append("class CustomFieldCreate {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    entity: ").append(toIndentedString(entity)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    placeholder: ").append(toIndentedString(placeholder)).append("\n");
    sb.append("    defaultValue: ").append(toIndentedString(defaultValue)).append("\n");
    sb.append("    isFilterable: ").append(toIndentedString(isFilterable)).append("\n");
    sb.append("    isVisible: ").append(toIndentedString(isVisible)).append("\n");
    sb.append("    isRequired: ").append(toIndentedString(isRequired)).append("\n");
    sb.append("    isEnabledForAllProjects: ").append(toIndentedString(isEnabledForAllProjects)).append("\n");
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
    openapiFields.add("title");
    openapiFields.add("value");
    openapiFields.add("entity");
    openapiFields.add("type");
    openapiFields.add("placeholder");
    openapiFields.add("default_value");
    openapiFields.add("is_filterable");
    openapiFields.add("is_visible");
    openapiFields.add("is_required");
    openapiFields.add("is_enabled_for_all_projects");
    openapiFields.add("projects_codes");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("title");
    openapiRequiredFields.add("entity");
    openapiRequiredFields.add("type");
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CustomFieldCreate.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CustomFieldCreate' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CustomFieldCreate> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CustomFieldCreate.class));

       return (TypeAdapter<T>) new TypeAdapter<CustomFieldCreate>() {
           @Override
           public void write(JsonWriter out, CustomFieldCreate value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CustomFieldCreate read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CustomFieldCreate given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CustomFieldCreate
  * @throws IOException if the JSON string is invalid with respect to CustomFieldCreate
  */
  public static CustomFieldCreate fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CustomFieldCreate.class);
  }

 /**
  * Convert an instance of CustomFieldCreate to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

