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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * RunCreate
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class RunCreate {
  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_INCLUDE_ALL_CASES = "include_all_cases";
  @SerializedName(SERIALIZED_NAME_INCLUDE_ALL_CASES)
  private Boolean includeAllCases;

  public static final String SERIALIZED_NAME_CASES = "cases";
  @SerializedName(SERIALIZED_NAME_CASES)
  private List<Long> cases;

  public static final String SERIALIZED_NAME_IS_AUTOTEST = "is_autotest";
  @SerializedName(SERIALIZED_NAME_IS_AUTOTEST)
  private Boolean isAutotest;

  public static final String SERIALIZED_NAME_ENVIRONMENT_ID = "environment_id";
  @SerializedName(SERIALIZED_NAME_ENVIRONMENT_ID)
  private Long environmentId;

  public static final String SERIALIZED_NAME_ENVIRONMENT_SLUG = "environment_slug";
  @SerializedName(SERIALIZED_NAME_ENVIRONMENT_SLUG)
  private String environmentSlug;

  public static final String SERIALIZED_NAME_MILESTONE_ID = "milestone_id";
  @SerializedName(SERIALIZED_NAME_MILESTONE_ID)
  private Long milestoneId;

  public static final String SERIALIZED_NAME_PLAN_ID = "plan_id";
  @SerializedName(SERIALIZED_NAME_PLAN_ID)
  private Long planId;

  public static final String SERIALIZED_NAME_AUTHOR_ID = "author_id";
  @SerializedName(SERIALIZED_NAME_AUTHOR_ID)
  private Long authorId;

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<String> tags;

  public static final String SERIALIZED_NAME_CONFIGURATIONS = "configurations";
  @SerializedName(SERIALIZED_NAME_CONFIGURATIONS)
  private List<Long> configurations;

  public static final String SERIALIZED_NAME_CUSTOM_FIELD = "custom_field";
  @SerializedName(SERIALIZED_NAME_CUSTOM_FIELD)
  private Map<String, String> customField = new HashMap<>();

  public static final String SERIALIZED_NAME_START_TIME = "start_time";
  @SerializedName(SERIALIZED_NAME_START_TIME)
  private String startTime;

  public static final String SERIALIZED_NAME_END_TIME = "end_time";
  @SerializedName(SERIALIZED_NAME_END_TIME)
  private String endTime;

  public RunCreate() {
  }

  public RunCreate title(String title) {
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


  public RunCreate description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public RunCreate includeAllCases(Boolean includeAllCases) {
    this.includeAllCases = includeAllCases;
    return this;
  }

   /**
   * Get includeAllCases
   * @return includeAllCases
  **/
  @javax.annotation.Nullable
  public Boolean getIncludeAllCases() {
    return includeAllCases;
  }

  public void setIncludeAllCases(Boolean includeAllCases) {
    this.includeAllCases = includeAllCases;
  }


  public RunCreate cases(List<Long> cases) {
    this.cases = cases;
    return this;
  }

  public RunCreate addCasesItem(Long casesItem) {
    if (this.cases == null) {
      this.cases = new ArrayList<>();
    }
    this.cases.add(casesItem);
    return this;
  }

   /**
   * Get cases
   * @return cases
  **/
  @javax.annotation.Nullable
  public List<Long> getCases() {
    return cases;
  }

  public void setCases(List<Long> cases) {
    this.cases = cases;
  }


  public RunCreate isAutotest(Boolean isAutotest) {
    this.isAutotest = isAutotest;
    return this;
  }

   /**
   * Get isAutotest
   * @return isAutotest
  **/
  @javax.annotation.Nullable
  public Boolean getIsAutotest() {
    return isAutotest;
  }

  public void setIsAutotest(Boolean isAutotest) {
    this.isAutotest = isAutotest;
  }


  public RunCreate environmentId(Long environmentId) {
    this.environmentId = environmentId;
    return this;
  }

   /**
   * Get environmentId
   * minimum: 1
   * @return environmentId
  **/
  @javax.annotation.Nullable
  public Long getEnvironmentId() {
    return environmentId;
  }

  public void setEnvironmentId(Long environmentId) {
    this.environmentId = environmentId;
  }


  public RunCreate environmentSlug(String environmentSlug) {
    this.environmentSlug = environmentSlug;
    return this;
  }

   /**
   * Get environmentSlug
   * @return environmentSlug
  **/
  @javax.annotation.Nullable
  public String getEnvironmentSlug() {
    return environmentSlug;
  }

  public void setEnvironmentSlug(String environmentSlug) {
    this.environmentSlug = environmentSlug;
  }


  public RunCreate milestoneId(Long milestoneId) {
    this.milestoneId = milestoneId;
    return this;
  }

   /**
   * Get milestoneId
   * minimum: 1
   * @return milestoneId
  **/
  @javax.annotation.Nullable
  public Long getMilestoneId() {
    return milestoneId;
  }

  public void setMilestoneId(Long milestoneId) {
    this.milestoneId = milestoneId;
  }


  public RunCreate planId(Long planId) {
    this.planId = planId;
    return this;
  }

   /**
   * Get planId
   * minimum: 1
   * @return planId
  **/
  @javax.annotation.Nullable
  public Long getPlanId() {
    return planId;
  }

  public void setPlanId(Long planId) {
    this.planId = planId;
  }


  public RunCreate authorId(Long authorId) {
    this.authorId = authorId;
    return this;
  }

   /**
   * Get authorId
   * minimum: 1
   * @return authorId
  **/
  @javax.annotation.Nullable
  public Long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }


  public RunCreate tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public RunCreate addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Get tags
   * @return tags
  **/
  @javax.annotation.Nullable
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }


  public RunCreate configurations(List<Long> configurations) {
    this.configurations = configurations;
    return this;
  }

  public RunCreate addConfigurationsItem(Long configurationsItem) {
    if (this.configurations == null) {
      this.configurations = new ArrayList<>();
    }
    this.configurations.add(configurationsItem);
    return this;
  }

   /**
   * Get configurations
   * @return configurations
  **/
  @javax.annotation.Nullable
  public List<Long> getConfigurations() {
    return configurations;
  }

  public void setConfigurations(List<Long> configurations) {
    this.configurations = configurations;
  }


  public RunCreate customField(Map<String, String> customField) {
    this.customField = customField;
    return this;
  }

  public RunCreate putCustomFieldItem(String key, String customFieldItem) {
    if (this.customField == null) {
      this.customField = new HashMap<>();
    }
    this.customField.put(key, customFieldItem);
    return this;
  }

   /**
   * A map of custom fields values (id &#x3D;&gt; value)
   * @return customField
  **/
  @javax.annotation.Nullable
  public Map<String, String> getCustomField() {
    return customField;
  }

  public void setCustomField(Map<String, String> customField) {
    this.customField = customField;
  }


  public RunCreate startTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * Get startTime
   * @return startTime
  **/
  @javax.annotation.Nullable
  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }


  public RunCreate endTime(String endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * Get endTime
   * @return endTime
  **/
  @javax.annotation.Nullable
  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  /**
   * A container for additional, undeclared properties.
   * This is a holder for any undeclared properties as specified with
   * the 'additionalProperties' keyword in the OAS document.
   */
  private Map<String, Object> additionalProperties;

  /**
   * Set the additional (undeclared) property with the specified name and value.
   * If the property does not already exist, create it otherwise replace it.
   *
   * @param key name of the property
   * @param value value of the property
   * @return the RunCreate instance itself
   */
  public RunCreate putAdditionalProperty(String key, Object value) {
    if (this.additionalProperties == null) {
        this.additionalProperties = new HashMap<String, Object>();
    }
    this.additionalProperties.put(key, value);
    return this;
  }

  /**
   * Return the additional (undeclared) property.
   *
   * @return a map of objects
   */
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  /**
   * Return the additional (undeclared) property with the specified name.
   *
   * @param key name of the property
   * @return an object
   */
  public Object getAdditionalProperty(String key) {
    if (this.additionalProperties == null) {
        return null;
    }
    return this.additionalProperties.get(key);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RunCreate runCreate = (RunCreate) o;
    return Objects.equals(this.title, runCreate.title) &&
        Objects.equals(this.description, runCreate.description) &&
        Objects.equals(this.includeAllCases, runCreate.includeAllCases) &&
        Objects.equals(this.cases, runCreate.cases) &&
        Objects.equals(this.isAutotest, runCreate.isAutotest) &&
        Objects.equals(this.environmentId, runCreate.environmentId) &&
        Objects.equals(this.environmentSlug, runCreate.environmentSlug) &&
        Objects.equals(this.milestoneId, runCreate.milestoneId) &&
        Objects.equals(this.planId, runCreate.planId) &&
        Objects.equals(this.authorId, runCreate.authorId) &&
        Objects.equals(this.tags, runCreate.tags) &&
        Objects.equals(this.configurations, runCreate.configurations) &&
        Objects.equals(this.customField, runCreate.customField) &&
        Objects.equals(this.startTime, runCreate.startTime) &&
        Objects.equals(this.endTime, runCreate.endTime)&&
        Objects.equals(this.additionalProperties, runCreate.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, includeAllCases, cases, isAutotest, environmentId, environmentSlug, milestoneId, planId, authorId, tags, configurations, customField, startTime, endTime, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RunCreate {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    includeAllCases: ").append(toIndentedString(includeAllCases)).append("\n");
    sb.append("    cases: ").append(toIndentedString(cases)).append("\n");
    sb.append("    isAutotest: ").append(toIndentedString(isAutotest)).append("\n");
    sb.append("    environmentId: ").append(toIndentedString(environmentId)).append("\n");
    sb.append("    environmentSlug: ").append(toIndentedString(environmentSlug)).append("\n");
    sb.append("    milestoneId: ").append(toIndentedString(milestoneId)).append("\n");
    sb.append("    planId: ").append(toIndentedString(planId)).append("\n");
    sb.append("    authorId: ").append(toIndentedString(authorId)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    configurations: ").append(toIndentedString(configurations)).append("\n");
    sb.append("    customField: ").append(toIndentedString(customField)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    additionalProperties: ").append(toIndentedString(additionalProperties)).append("\n");
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
    openapiFields.add("description");
    openapiFields.add("include_all_cases");
    openapiFields.add("cases");
    openapiFields.add("is_autotest");
    openapiFields.add("environment_id");
    openapiFields.add("environment_slug");
    openapiFields.add("milestone_id");
    openapiFields.add("plan_id");
    openapiFields.add("author_id");
    openapiFields.add("tags");
    openapiFields.add("configurations");
    openapiFields.add("custom_field");
    openapiFields.add("start_time");
    openapiFields.add("end_time");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("title");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to RunCreate
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!RunCreate.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in RunCreate is not found in the empty JSON string", RunCreate.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : RunCreate.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("title").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj.get("title").toString()));
      }
      if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull()) && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("cases") != null && !jsonObj.get("cases").isJsonNull() && !jsonObj.get("cases").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `cases` to be an array in the JSON string but got `%s`", jsonObj.get("cases").toString()));
      }
      if ((jsonObj.get("environment_slug") != null && !jsonObj.get("environment_slug").isJsonNull()) && !jsonObj.get("environment_slug").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `environment_slug` to be a primitive type in the JSON string but got `%s`", jsonObj.get("environment_slug").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("tags") != null && !jsonObj.get("tags").isJsonNull() && !jsonObj.get("tags").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `tags` to be an array in the JSON string but got `%s`", jsonObj.get("tags").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("configurations") != null && !jsonObj.get("configurations").isJsonNull() && !jsonObj.get("configurations").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `configurations` to be an array in the JSON string but got `%s`", jsonObj.get("configurations").toString()));
      }
      if ((jsonObj.get("start_time") != null && !jsonObj.get("start_time").isJsonNull()) && !jsonObj.get("start_time").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `start_time` to be a primitive type in the JSON string but got `%s`", jsonObj.get("start_time").toString()));
      }
      if ((jsonObj.get("end_time") != null && !jsonObj.get("end_time").isJsonNull()) && !jsonObj.get("end_time").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `end_time` to be a primitive type in the JSON string but got `%s`", jsonObj.get("end_time").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!RunCreate.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'RunCreate' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<RunCreate> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(RunCreate.class));

       return (TypeAdapter<T>) new TypeAdapter<RunCreate>() {
           @Override
           public void write(JsonWriter out, RunCreate value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             obj.remove("additionalProperties");
             // serialize additional properties
             if (value.getAdditionalProperties() != null) {
               for (Map.Entry<String, Object> entry : value.getAdditionalProperties().entrySet()) {
                 if (entry.getValue() instanceof String)
                   obj.addProperty(entry.getKey(), (String) entry.getValue());
                 else if (entry.getValue() instanceof Number)
                   obj.addProperty(entry.getKey(), (Number) entry.getValue());
                 else if (entry.getValue() instanceof Boolean)
                   obj.addProperty(entry.getKey(), (Boolean) entry.getValue());
                 else if (entry.getValue() instanceof Character)
                   obj.addProperty(entry.getKey(), (Character) entry.getValue());
                 else {
                   JsonElement jsonElement = gson.toJsonTree(entry.getValue());
                   if (jsonElement.isJsonArray()) {
                     obj.add(entry.getKey(), jsonElement.getAsJsonArray());
                   } else {
                     obj.add(entry.getKey(), jsonElement.getAsJsonObject());
                   }
                 }
               }
             }
             elementAdapter.write(out, obj);
           }

           @Override
           public RunCreate read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             RunCreate instance = thisAdapter.fromJsonTree(jsonObj);
             for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
               if (!openapiFields.contains(entry.getKey())) {
                 if (entry.getValue().isJsonPrimitive()) { // primitive type
                   if (entry.getValue().getAsJsonPrimitive().isString())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsString());
                   else if (entry.getValue().getAsJsonPrimitive().isNumber())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsNumber());
                   else if (entry.getValue().getAsJsonPrimitive().isBoolean())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsBoolean());
                   else
                     throw new IllegalArgumentException(String.format("The field `%s` has unknown primitive type. Value: %s", entry.getKey(), entry.getValue().toString()));
                 } else if (entry.getValue().isJsonArray()) {
                     instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), List.class));
                 } else { // JSON object
                     instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), HashMap.class));
                 }
               }
             }
             return instance;
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of RunCreate given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of RunCreate
  * @throws IOException if the JSON string is invalid with respect to RunCreate
  */
  public static RunCreate fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, RunCreate.class);
  }

 /**
  * Convert an instance of RunCreate to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

