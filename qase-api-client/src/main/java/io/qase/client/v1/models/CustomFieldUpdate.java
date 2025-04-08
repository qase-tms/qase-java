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
import io.qase.client.v1.models.CustomFieldCreateValueInner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * CustomFieldUpdate
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class CustomFieldUpdate {
  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_VALUE = "value";
  @SerializedName(SERIALIZED_NAME_VALUE)
  private List<CustomFieldCreateValueInner> value;

  public static final String SERIALIZED_NAME_REPLACE_VALUES = "replace_values";
  @SerializedName(SERIALIZED_NAME_REPLACE_VALUES)
  private Map<String, String> replaceValues;

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
  private List<String> projectsCodes;

  public CustomFieldUpdate() {
  }

  public CustomFieldUpdate title(String title) {
    this.title = title;
    return this;
  }

   /**
   * Get title
   * @return title
  **/
  @javax.annotation.Nonnull
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public CustomFieldUpdate value(List<CustomFieldCreateValueInner> value) {
    this.value = value;
    return this;
  }

  public CustomFieldUpdate addValueItem(CustomFieldCreateValueInner valueItem) {
    if (this.value == null) {
      this.value = new ArrayList<>();
    }
    this.value.add(valueItem);
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @javax.annotation.Nullable
  public List<CustomFieldCreateValueInner> getValue() {
    return value;
  }

  public void setValue(List<CustomFieldCreateValueInner> value) {
    this.value = value;
  }


  public CustomFieldUpdate replaceValues(Map<String, String> replaceValues) {
    this.replaceValues = replaceValues;
    return this;
  }

  public CustomFieldUpdate putReplaceValuesItem(String key, String replaceValuesItem) {
    if (this.replaceValues == null) {
      this.replaceValues = new HashMap<>();
    }
    this.replaceValues.put(key, replaceValuesItem);
    return this;
  }

   /**
   * Dictionary of old values and their replacemants
   * @return replaceValues
  **/
  @javax.annotation.Nullable
  public Map<String, String> getReplaceValues() {
    return replaceValues;
  }

  public void setReplaceValues(Map<String, String> replaceValues) {
    this.replaceValues = replaceValues;
  }


  public CustomFieldUpdate placeholder(String placeholder) {
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


  public CustomFieldUpdate defaultValue(String defaultValue) {
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


  public CustomFieldUpdate isFilterable(Boolean isFilterable) {
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


  public CustomFieldUpdate isVisible(Boolean isVisible) {
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


  public CustomFieldUpdate isRequired(Boolean isRequired) {
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


  public CustomFieldUpdate isEnabledForAllProjects(Boolean isEnabledForAllProjects) {
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


  public CustomFieldUpdate projectsCodes(List<String> projectsCodes) {
    this.projectsCodes = projectsCodes;
    return this;
  }

  public CustomFieldUpdate addProjectsCodesItem(String projectsCodesItem) {
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
    CustomFieldUpdate customFieldUpdate = (CustomFieldUpdate) o;
    return Objects.equals(this.title, customFieldUpdate.title) &&
        Objects.equals(this.value, customFieldUpdate.value) &&
        Objects.equals(this.replaceValues, customFieldUpdate.replaceValues) &&
        Objects.equals(this.placeholder, customFieldUpdate.placeholder) &&
        Objects.equals(this.defaultValue, customFieldUpdate.defaultValue) &&
        Objects.equals(this.isFilterable, customFieldUpdate.isFilterable) &&
        Objects.equals(this.isVisible, customFieldUpdate.isVisible) &&
        Objects.equals(this.isRequired, customFieldUpdate.isRequired) &&
        Objects.equals(this.isEnabledForAllProjects, customFieldUpdate.isEnabledForAllProjects) &&
        Objects.equals(this.projectsCodes, customFieldUpdate.projectsCodes);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, value, replaceValues, placeholder, defaultValue, isFilterable, isVisible, isRequired, isEnabledForAllProjects, projectsCodes);
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
    sb.append("class CustomFieldUpdate {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    replaceValues: ").append(toIndentedString(replaceValues)).append("\n");
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
    openapiFields.add("replace_values");
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
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to CustomFieldUpdate
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!CustomFieldUpdate.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in CustomFieldUpdate is not found in the empty JSON string", CustomFieldUpdate.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!CustomFieldUpdate.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CustomFieldUpdate` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CustomFieldUpdate.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("title").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj.get("title").toString()));
      }
      if (jsonObj.get("value") != null && !jsonObj.get("value").isJsonNull()) {
        JsonArray jsonArrayvalue = jsonObj.getAsJsonArray("value");
        if (jsonArrayvalue != null) {
          // ensure the json data is an array
          if (!jsonObj.get("value").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `value` to be an array in the JSON string but got `%s`", jsonObj.get("value").toString()));
          }

          // validate the optional field `value` (array)
          for (int i = 0; i < jsonArrayvalue.size(); i++) {
            CustomFieldCreateValueInner.validateJsonElement(jsonArrayvalue.get(i));
          };
        }
      }
      if ((jsonObj.get("placeholder") != null && !jsonObj.get("placeholder").isJsonNull()) && !jsonObj.get("placeholder").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `placeholder` to be a primitive type in the JSON string but got `%s`", jsonObj.get("placeholder").toString()));
      }
      if ((jsonObj.get("default_value") != null && !jsonObj.get("default_value").isJsonNull()) && !jsonObj.get("default_value").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `default_value` to be a primitive type in the JSON string but got `%s`", jsonObj.get("default_value").toString()));
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
       if (!CustomFieldUpdate.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CustomFieldUpdate' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CustomFieldUpdate> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CustomFieldUpdate.class));

       return (TypeAdapter<T>) new TypeAdapter<CustomFieldUpdate>() {
           @Override
           public void write(JsonWriter out, CustomFieldUpdate value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CustomFieldUpdate read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CustomFieldUpdate given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CustomFieldUpdate
  * @throws IOException if the JSON string is invalid with respect to CustomFieldUpdate
  */
  public static CustomFieldUpdate fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CustomFieldUpdate.class);
  }

 /**
  * Convert an instance of CustomFieldUpdate to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

