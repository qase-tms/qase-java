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
import java.io.IOException;
import java.util.Arrays;

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
 * ResultCreateFields
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class ResultCreateFields {
  public static final String SERIALIZED_NAME_AUTHOR = "author";
  @SerializedName(SERIALIZED_NAME_AUTHOR)
  private String author;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_PRECONDITIONS = "preconditions";
  @SerializedName(SERIALIZED_NAME_PRECONDITIONS)
  private String preconditions;

  public static final String SERIALIZED_NAME_POSTCONDITIONS = "postconditions";
  @SerializedName(SERIALIZED_NAME_POSTCONDITIONS)
  private String postconditions;

  public static final String SERIALIZED_NAME_LAYER = "layer";
  @SerializedName(SERIALIZED_NAME_LAYER)
  private String layer;

  public static final String SERIALIZED_NAME_SEVERITY = "severity";
  @SerializedName(SERIALIZED_NAME_SEVERITY)
  private String severity;

  public static final String SERIALIZED_NAME_PRIORITY = "priority";
  @SerializedName(SERIALIZED_NAME_PRIORITY)
  private String priority;

  public static final String SERIALIZED_NAME_BEHAVIOR = "behavior";
  @SerializedName(SERIALIZED_NAME_BEHAVIOR)
  private String behavior;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private String type;

  public static final String SERIALIZED_NAME_MUTED = "muted";
  @SerializedName(SERIALIZED_NAME_MUTED)
  private String muted;

  public static final String SERIALIZED_NAME_IS_FLAKY = "is_flaky";
  @SerializedName(SERIALIZED_NAME_IS_FLAKY)
  private String isFlaky;

  public static final String SERIALIZED_NAME_EXECUTED_BY = "executed_by";
  @SerializedName(SERIALIZED_NAME_EXECUTED_BY)
  private String executedBy;

  public ResultCreateFields() {
  }

  public ResultCreateFields author(String author) {
    this.author = author;
    return this;
  }

   /**
   * Author of the related test case (member id, name or email). If set and test case auto-creation is enabled, the author will be used to create the test case
   * @return author
  **/
  @javax.annotation.Nullable
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }


  public ResultCreateFields description(String description) {
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


  public ResultCreateFields preconditions(String preconditions) {
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


  public ResultCreateFields postconditions(String postconditions) {
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


  public ResultCreateFields layer(String layer) {
    this.layer = layer;
    return this;
  }

   /**
   * Get layer
   * @return layer
  **/
  @javax.annotation.Nullable
  public String getLayer() {
    return layer;
  }

  public void setLayer(String layer) {
    this.layer = layer;
  }


  public ResultCreateFields severity(String severity) {
    this.severity = severity;
    return this;
  }

   /**
   * Get severity
   * @return severity
  **/
  @javax.annotation.Nullable
  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }


  public ResultCreateFields priority(String priority) {
    this.priority = priority;
    return this;
  }

   /**
   * Get priority
   * @return priority
  **/
  @javax.annotation.Nullable
  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }


  public ResultCreateFields behavior(String behavior) {
    this.behavior = behavior;
    return this;
  }

   /**
   * Get behavior
   * @return behavior
  **/
  @javax.annotation.Nullable
  public String getBehavior() {
    return behavior;
  }

  public void setBehavior(String behavior) {
    this.behavior = behavior;
  }


  public ResultCreateFields type(String type) {
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


  public ResultCreateFields muted(String muted) {
    this.muted = muted;
    return this;
  }

   /**
   * Get muted
   * @return muted
  **/
  @javax.annotation.Nullable
  public String getMuted() {
    return muted;
  }

  public void setMuted(String muted) {
    this.muted = muted;
  }


  public ResultCreateFields isFlaky(String isFlaky) {
    this.isFlaky = isFlaky;
    return this;
  }

   /**
   * Get isFlaky
   * @return isFlaky
  **/
  @javax.annotation.Nullable
  public String getIsFlaky() {
    return isFlaky;
  }

  public void setIsFlaky(String isFlaky) {
    this.isFlaky = isFlaky;
  }


  public ResultCreateFields executedBy(String executedBy) {
    this.executedBy = executedBy;
    return this;
  }

   /**
   * User who executed the test (member id, name or email)
   * @return executedBy
  **/
  @javax.annotation.Nullable
  public String getExecutedBy() {
    return executedBy;
  }

  public void setExecutedBy(String executedBy) {
    this.executedBy = executedBy;
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
   * @return the ResultCreateFields instance itself
   */
  public ResultCreateFields putAdditionalProperty(String key, Object value) {
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
    ResultCreateFields resultCreateFields = (ResultCreateFields) o;
    return Objects.equals(this.author, resultCreateFields.author) &&
        Objects.equals(this.description, resultCreateFields.description) &&
        Objects.equals(this.preconditions, resultCreateFields.preconditions) &&
        Objects.equals(this.postconditions, resultCreateFields.postconditions) &&
        Objects.equals(this.layer, resultCreateFields.layer) &&
        Objects.equals(this.severity, resultCreateFields.severity) &&
        Objects.equals(this.priority, resultCreateFields.priority) &&
        Objects.equals(this.behavior, resultCreateFields.behavior) &&
        Objects.equals(this.type, resultCreateFields.type) &&
        Objects.equals(this.muted, resultCreateFields.muted) &&
        Objects.equals(this.isFlaky, resultCreateFields.isFlaky) &&
        Objects.equals(this.executedBy, resultCreateFields.executedBy)&&
        Objects.equals(this.additionalProperties, resultCreateFields.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, description, preconditions, postconditions, layer, severity, priority, behavior, type, muted, isFlaky, executedBy, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResultCreateFields {\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    preconditions: ").append(toIndentedString(preconditions)).append("\n");
    sb.append("    postconditions: ").append(toIndentedString(postconditions)).append("\n");
    sb.append("    layer: ").append(toIndentedString(layer)).append("\n");
    sb.append("    severity: ").append(toIndentedString(severity)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
    sb.append("    behavior: ").append(toIndentedString(behavior)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    muted: ").append(toIndentedString(muted)).append("\n");
    sb.append("    isFlaky: ").append(toIndentedString(isFlaky)).append("\n");
    sb.append("    executedBy: ").append(toIndentedString(executedBy)).append("\n");
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
    openapiFields.add("author");
    openapiFields.add("description");
    openapiFields.add("preconditions");
    openapiFields.add("postconditions");
    openapiFields.add("layer");
    openapiFields.add("severity");
    openapiFields.add("priority");
    openapiFields.add("behavior");
    openapiFields.add("type");
    openapiFields.add("muted");
    openapiFields.add("is_flaky");
    openapiFields.add("executed_by");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ResultCreateFields
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ResultCreateFields.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ResultCreateFields is not found in the empty JSON string", ResultCreateFields.openapiRequiredFields.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("author") != null && !jsonObj.get("author").isJsonNull()) && !jsonObj.get("author").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `author` to be a primitive type in the JSON string but got `%s`", jsonObj.get("author").toString()));
      }
      if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull()) && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if ((jsonObj.get("preconditions") != null && !jsonObj.get("preconditions").isJsonNull()) && !jsonObj.get("preconditions").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `preconditions` to be a primitive type in the JSON string but got `%s`", jsonObj.get("preconditions").toString()));
      }
      if ((jsonObj.get("postconditions") != null && !jsonObj.get("postconditions").isJsonNull()) && !jsonObj.get("postconditions").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `postconditions` to be a primitive type in the JSON string but got `%s`", jsonObj.get("postconditions").toString()));
      }
      if ((jsonObj.get("layer") != null && !jsonObj.get("layer").isJsonNull()) && !jsonObj.get("layer").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `layer` to be a primitive type in the JSON string but got `%s`", jsonObj.get("layer").toString()));
      }
      if ((jsonObj.get("severity") != null && !jsonObj.get("severity").isJsonNull()) && !jsonObj.get("severity").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `severity` to be a primitive type in the JSON string but got `%s`", jsonObj.get("severity").toString()));
      }
      if ((jsonObj.get("priority") != null && !jsonObj.get("priority").isJsonNull()) && !jsonObj.get("priority").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `priority` to be a primitive type in the JSON string but got `%s`", jsonObj.get("priority").toString()));
      }
      if ((jsonObj.get("behavior") != null && !jsonObj.get("behavior").isJsonNull()) && !jsonObj.get("behavior").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `behavior` to be a primitive type in the JSON string but got `%s`", jsonObj.get("behavior").toString()));
      }
      if ((jsonObj.get("type") != null && !jsonObj.get("type").isJsonNull()) && !jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      if ((jsonObj.get("muted") != null && !jsonObj.get("muted").isJsonNull()) && !jsonObj.get("muted").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `muted` to be a primitive type in the JSON string but got `%s`", jsonObj.get("muted").toString()));
      }
      if ((jsonObj.get("is_flaky") != null && !jsonObj.get("is_flaky").isJsonNull()) && !jsonObj.get("is_flaky").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `is_flaky` to be a primitive type in the JSON string but got `%s`", jsonObj.get("is_flaky").toString()));
      }
      if ((jsonObj.get("executed_by") != null && !jsonObj.get("executed_by").isJsonNull()) && !jsonObj.get("executed_by").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `executed_by` to be a primitive type in the JSON string but got `%s`", jsonObj.get("executed_by").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ResultCreateFields.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ResultCreateFields' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ResultCreateFields> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ResultCreateFields.class));

       return (TypeAdapter<T>) new TypeAdapter<ResultCreateFields>() {
           @Override
           public void write(JsonWriter out, ResultCreateFields value) throws IOException {
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
           public ResultCreateFields read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             ResultCreateFields instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of ResultCreateFields given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ResultCreateFields
  * @throws IOException if the JSON string is invalid with respect to ResultCreateFields
  */
  public static ResultCreateFields fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ResultCreateFields.class);
  }

 /**
  * Convert an instance of ResultCreateFields to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

