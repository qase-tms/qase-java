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
import io.qase.client.v1.models.TestStepCreate;
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
 * TestCasebulkCasesInner
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class TestCasebulkCasesInner {
  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_PRECONDITIONS = "preconditions";
  @SerializedName(SERIALIZED_NAME_PRECONDITIONS)
  private String preconditions;

  public static final String SERIALIZED_NAME_POSTCONDITIONS = "postconditions";
  @SerializedName(SERIALIZED_NAME_POSTCONDITIONS)
  private String postconditions;

  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_SEVERITY = "severity";
  @SerializedName(SERIALIZED_NAME_SEVERITY)
  private Integer severity;

  public static final String SERIALIZED_NAME_PRIORITY = "priority";
  @SerializedName(SERIALIZED_NAME_PRIORITY)
  private Integer priority;

  public static final String SERIALIZED_NAME_BEHAVIOR = "behavior";
  @SerializedName(SERIALIZED_NAME_BEHAVIOR)
  private Integer behavior;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private Integer type;

  public static final String SERIALIZED_NAME_LAYER = "layer";
  @SerializedName(SERIALIZED_NAME_LAYER)
  private Integer layer;

  public static final String SERIALIZED_NAME_IS_FLAKY = "is_flaky";
  @SerializedName(SERIALIZED_NAME_IS_FLAKY)
  private Integer isFlaky;

  public static final String SERIALIZED_NAME_SUITE_ID = "suite_id";
  @SerializedName(SERIALIZED_NAME_SUITE_ID)
  private Long suiteId;

  public static final String SERIALIZED_NAME_MILESTONE_ID = "milestone_id";
  @SerializedName(SERIALIZED_NAME_MILESTONE_ID)
  private Long milestoneId;

  public static final String SERIALIZED_NAME_AUTOMATION = "automation";
  @SerializedName(SERIALIZED_NAME_AUTOMATION)
  private Integer automation;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private Integer status;

  public static final String SERIALIZED_NAME_ATTACHMENTS = "attachments";
  @SerializedName(SERIALIZED_NAME_ATTACHMENTS)
  private List<String> attachments;

  public static final String SERIALIZED_NAME_STEPS = "steps";
  @SerializedName(SERIALIZED_NAME_STEPS)
  private List<TestStepCreate> steps;

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<String> tags;

  public static final String SERIALIZED_NAME_PARAMS = "params";
  @SerializedName(SERIALIZED_NAME_PARAMS)
  private Map<String, List<String>> params;

  public static final String SERIALIZED_NAME_CUSTOM_FIELD = "custom_field";
  @SerializedName(SERIALIZED_NAME_CUSTOM_FIELD)
  private Map<String, String> customField = new HashMap<>();

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private String createdAt;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private String updatedAt;

  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public TestCasebulkCasesInner() {
  }

  public TestCasebulkCasesInner description(String description) {
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


  public TestCasebulkCasesInner preconditions(String preconditions) {
    this.preconditions = preconditions;
    return this;
  }

   /**
   * Get preconditions
   * @return preconditions
  **/
  @javax.annotation.Nullable
  public String getPreconditions() {
    return preconditions;
  }

  public void setPreconditions(String preconditions) {
    this.preconditions = preconditions;
  }


  public TestCasebulkCasesInner postconditions(String postconditions) {
    this.postconditions = postconditions;
    return this;
  }

   /**
   * Get postconditions
   * @return postconditions
  **/
  @javax.annotation.Nullable
  public String getPostconditions() {
    return postconditions;
  }

  public void setPostconditions(String postconditions) {
    this.postconditions = postconditions;
  }


  public TestCasebulkCasesInner title(String title) {
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


  public TestCasebulkCasesInner severity(Integer severity) {
    this.severity = severity;
    return this;
  }

   /**
   * Get severity
   * @return severity
  **/
  @javax.annotation.Nullable
  public Integer getSeverity() {
    return severity;
  }

  public void setSeverity(Integer severity) {
    this.severity = severity;
  }


  public TestCasebulkCasesInner priority(Integer priority) {
    this.priority = priority;
    return this;
  }

   /**
   * Get priority
   * @return priority
  **/
  @javax.annotation.Nullable
  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }


  public TestCasebulkCasesInner behavior(Integer behavior) {
    this.behavior = behavior;
    return this;
  }

   /**
   * Get behavior
   * @return behavior
  **/
  @javax.annotation.Nullable
  public Integer getBehavior() {
    return behavior;
  }

  public void setBehavior(Integer behavior) {
    this.behavior = behavior;
  }


  public TestCasebulkCasesInner type(Integer type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nullable
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }


  public TestCasebulkCasesInner layer(Integer layer) {
    this.layer = layer;
    return this;
  }

   /**
   * Get layer
   * @return layer
  **/
  @javax.annotation.Nullable
  public Integer getLayer() {
    return layer;
  }

  public void setLayer(Integer layer) {
    this.layer = layer;
  }


  public TestCasebulkCasesInner isFlaky(Integer isFlaky) {
    this.isFlaky = isFlaky;
    return this;
  }

   /**
   * Get isFlaky
   * @return isFlaky
  **/
  @javax.annotation.Nullable
  public Integer getIsFlaky() {
    return isFlaky;
  }

  public void setIsFlaky(Integer isFlaky) {
    this.isFlaky = isFlaky;
  }


  public TestCasebulkCasesInner suiteId(Long suiteId) {
    this.suiteId = suiteId;
    return this;
  }

   /**
   * Get suiteId
   * @return suiteId
  **/
  @javax.annotation.Nullable
  public Long getSuiteId() {
    return suiteId;
  }

  public void setSuiteId(Long suiteId) {
    this.suiteId = suiteId;
  }


  public TestCasebulkCasesInner milestoneId(Long milestoneId) {
    this.milestoneId = milestoneId;
    return this;
  }

   /**
   * Get milestoneId
   * @return milestoneId
  **/
  @javax.annotation.Nullable
  public Long getMilestoneId() {
    return milestoneId;
  }

  public void setMilestoneId(Long milestoneId) {
    this.milestoneId = milestoneId;
  }


  public TestCasebulkCasesInner automation(Integer automation) {
    this.automation = automation;
    return this;
  }

   /**
   * Get automation
   * @return automation
  **/
  @javax.annotation.Nullable
  public Integer getAutomation() {
    return automation;
  }

  public void setAutomation(Integer automation) {
    this.automation = automation;
  }


  public TestCasebulkCasesInner status(Integer status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @javax.annotation.Nullable
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  public TestCasebulkCasesInner attachments(List<String> attachments) {
    this.attachments = attachments;
    return this;
  }

  public TestCasebulkCasesInner addAttachmentsItem(String attachmentsItem) {
    if (this.attachments == null) {
      this.attachments = new ArrayList<>();
    }
    this.attachments.add(attachmentsItem);
    return this;
  }

   /**
   * A list of Attachment hashes.
   * @return attachments
  **/
  @javax.annotation.Nullable
  public List<String> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<String> attachments) {
    this.attachments = attachments;
  }


  public TestCasebulkCasesInner steps(List<TestStepCreate> steps) {
    this.steps = steps;
    return this;
  }

  public TestCasebulkCasesInner addStepsItem(TestStepCreate stepsItem) {
    if (this.steps == null) {
      this.steps = new ArrayList<>();
    }
    this.steps.add(stepsItem);
    return this;
  }

   /**
   * Get steps
   * @return steps
  **/
  @javax.annotation.Nullable
  public List<TestStepCreate> getSteps() {
    return steps;
  }

  public void setSteps(List<TestStepCreate> steps) {
    this.steps = steps;
  }


  public TestCasebulkCasesInner tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public TestCasebulkCasesInner addTagsItem(String tagsItem) {
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


  public TestCasebulkCasesInner params(Map<String, List<String>> params) {
    this.params = params;
    return this;
  }

  public TestCasebulkCasesInner putParamsItem(String key, List<String> paramsItem) {
    if (this.params == null) {
      this.params = new HashMap<>();
    }
    this.params.put(key, paramsItem);
    return this;
  }

   /**
   * Get params
   * @return params
  **/
  @javax.annotation.Nullable
  public Map<String, List<String>> getParams() {
    return params;
  }

  public void setParams(Map<String, List<String>> params) {
    this.params = params;
  }


  public TestCasebulkCasesInner customField(Map<String, String> customField) {
    this.customField = customField;
    return this;
  }

  public TestCasebulkCasesInner putCustomFieldItem(String key, String customFieldItem) {
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


  public TestCasebulkCasesInner createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Get createdAt
   * @return createdAt
  **/
  @javax.annotation.Nullable
  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }


  public TestCasebulkCasesInner updatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Get updatedAt
   * @return updatedAt
  **/
  @javax.annotation.Nullable
  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }


  public TestCasebulkCasesInner id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
   * @return the TestCasebulkCasesInner instance itself
   */
  public TestCasebulkCasesInner putAdditionalProperty(String key, Object value) {
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
    TestCasebulkCasesInner testCasebulkCasesInner = (TestCasebulkCasesInner) o;
    return Objects.equals(this.description, testCasebulkCasesInner.description) &&
        Objects.equals(this.preconditions, testCasebulkCasesInner.preconditions) &&
        Objects.equals(this.postconditions, testCasebulkCasesInner.postconditions) &&
        Objects.equals(this.title, testCasebulkCasesInner.title) &&
        Objects.equals(this.severity, testCasebulkCasesInner.severity) &&
        Objects.equals(this.priority, testCasebulkCasesInner.priority) &&
        Objects.equals(this.behavior, testCasebulkCasesInner.behavior) &&
        Objects.equals(this.type, testCasebulkCasesInner.type) &&
        Objects.equals(this.layer, testCasebulkCasesInner.layer) &&
        Objects.equals(this.isFlaky, testCasebulkCasesInner.isFlaky) &&
        Objects.equals(this.suiteId, testCasebulkCasesInner.suiteId) &&
        Objects.equals(this.milestoneId, testCasebulkCasesInner.milestoneId) &&
        Objects.equals(this.automation, testCasebulkCasesInner.automation) &&
        Objects.equals(this.status, testCasebulkCasesInner.status) &&
        Objects.equals(this.attachments, testCasebulkCasesInner.attachments) &&
        Objects.equals(this.steps, testCasebulkCasesInner.steps) &&
        Objects.equals(this.tags, testCasebulkCasesInner.tags) &&
        Objects.equals(this.params, testCasebulkCasesInner.params) &&
        Objects.equals(this.customField, testCasebulkCasesInner.customField) &&
        Objects.equals(this.createdAt, testCasebulkCasesInner.createdAt) &&
        Objects.equals(this.updatedAt, testCasebulkCasesInner.updatedAt) &&
        Objects.equals(this.id, testCasebulkCasesInner.id)&&
        Objects.equals(this.additionalProperties, testCasebulkCasesInner.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, preconditions, postconditions, title, severity, priority, behavior, type, layer, isFlaky, suiteId, milestoneId, automation, status, attachments, steps, tags, params, customField, createdAt, updatedAt, id, additionalProperties);
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
    sb.append("class TestCasebulkCasesInner {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    preconditions: ").append(toIndentedString(preconditions)).append("\n");
    sb.append("    postconditions: ").append(toIndentedString(postconditions)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    severity: ").append(toIndentedString(severity)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    behavior: ").append(toIndentedString(behavior)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    layer: ").append(toIndentedString(layer)).append("\n");
    sb.append("    isFlaky: ").append(toIndentedString(isFlaky)).append("\n");
    sb.append("    suiteId: ").append(toIndentedString(suiteId)).append("\n");
    sb.append("    milestoneId: ").append(toIndentedString(milestoneId)).append("\n");
    sb.append("    automation: ").append(toIndentedString(automation)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    params: ").append(toIndentedString(params)).append("\n");
    sb.append("    customField: ").append(toIndentedString(customField)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
    openapiFields.add("description");
    openapiFields.add("preconditions");
    openapiFields.add("postconditions");
    openapiFields.add("title");
    openapiFields.add("severity");
    openapiFields.add("priority");
    openapiFields.add("behavior");
    openapiFields.add("type");
    openapiFields.add("layer");
    openapiFields.add("is_flaky");
    openapiFields.add("suite_id");
    openapiFields.add("milestone_id");
    openapiFields.add("automation");
    openapiFields.add("status");
    openapiFields.add("attachments");
    openapiFields.add("steps");
    openapiFields.add("tags");
    openapiFields.add("params");
    openapiFields.add("custom_field");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");
    openapiFields.add("id");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("title");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to TestCasebulkCasesInner
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!TestCasebulkCasesInner.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in TestCasebulkCasesInner is not found in the empty JSON string", TestCasebulkCasesInner.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : TestCasebulkCasesInner.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull()) && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if ((jsonObj.get("preconditions") != null && !jsonObj.get("preconditions").isJsonNull()) && !jsonObj.get("preconditions").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `preconditions` to be a primitive type in the JSON string but got `%s`", jsonObj.get("preconditions").toString()));
      }
      if ((jsonObj.get("postconditions") != null && !jsonObj.get("postconditions").isJsonNull()) && !jsonObj.get("postconditions").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `postconditions` to be a primitive type in the JSON string but got `%s`", jsonObj.get("postconditions").toString()));
      }
      if (!jsonObj.get("title").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj.get("title").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("attachments") != null && !jsonObj.get("attachments").isJsonNull() && !jsonObj.get("attachments").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `attachments` to be an array in the JSON string but got `%s`", jsonObj.get("attachments").toString()));
      }
      if (jsonObj.get("steps") != null && !jsonObj.get("steps").isJsonNull()) {
        JsonArray jsonArraysteps = jsonObj.getAsJsonArray("steps");
        if (jsonArraysteps != null) {
          // ensure the json data is an array
          if (!jsonObj.get("steps").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `steps` to be an array in the JSON string but got `%s`", jsonObj.get("steps").toString()));
          }

          // validate the optional field `steps` (array)
          for (int i = 0; i < jsonArraysteps.size(); i++) {
            TestStepCreate.validateJsonElement(jsonArraysteps.get(i));
          };
        }
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("tags") != null && !jsonObj.get("tags").isJsonNull() && !jsonObj.get("tags").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `tags` to be an array in the JSON string but got `%s`", jsonObj.get("tags").toString()));
      }
      if ((jsonObj.get("created_at") != null && !jsonObj.get("created_at").isJsonNull()) && !jsonObj.get("created_at").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `created_at` to be a primitive type in the JSON string but got `%s`", jsonObj.get("created_at").toString()));
      }
      if ((jsonObj.get("updated_at") != null && !jsonObj.get("updated_at").isJsonNull()) && !jsonObj.get("updated_at").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `updated_at` to be a primitive type in the JSON string but got `%s`", jsonObj.get("updated_at").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!TestCasebulkCasesInner.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'TestCasebulkCasesInner' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<TestCasebulkCasesInner> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(TestCasebulkCasesInner.class));

       return (TypeAdapter<T>) new TypeAdapter<TestCasebulkCasesInner>() {
           @Override
           public void write(JsonWriter out, TestCasebulkCasesInner value) throws IOException {
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
           public TestCasebulkCasesInner read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             TestCasebulkCasesInner instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of TestCasebulkCasesInner given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of TestCasebulkCasesInner
  * @throws IOException if the JSON string is invalid with respect to TestCasebulkCasesInner
  */
  public static TestCasebulkCasesInner fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, TestCasebulkCasesInner.class);
  }

 /**
  * Convert an instance of TestCasebulkCasesInner to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

