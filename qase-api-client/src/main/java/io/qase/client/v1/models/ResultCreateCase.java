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
import java.util.Arrays;
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
 * Could be used instead of &#x60;case_id&#x60;.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class ResultCreateCase {
  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_SUITE_TITLE = "suite_title";
  @SerializedName(SERIALIZED_NAME_SUITE_TITLE)
  private String suiteTitle;

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

  public ResultCreateCase() {
  }

  public ResultCreateCase title(String title) {
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


  public ResultCreateCase suiteTitle(String suiteTitle) {
    this.suiteTitle = suiteTitle;
    return this;
  }

   /**
   * Nested suites should be separated with &#x60;TAB&#x60; symbol.
   * @return suiteTitle
  **/
  @javax.annotation.Nullable
  public String getSuiteTitle() {
    return suiteTitle;
  }

  public void setSuiteTitle(String suiteTitle) {
    this.suiteTitle = suiteTitle;
  }


  public ResultCreateCase description(String description) {
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


  public ResultCreateCase preconditions(String preconditions) {
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


  public ResultCreateCase postconditions(String postconditions) {
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


  public ResultCreateCase layer(String layer) {
    this.layer = layer;
    return this;
  }

   /**
   * Slug of the layer. You can get it in the System Field settings.
   * @return layer
  **/
  @javax.annotation.Nullable
  public String getLayer() {
    return layer;
  }

  public void setLayer(String layer) {
    this.layer = layer;
  }


  public ResultCreateCase severity(String severity) {
    this.severity = severity;
    return this;
  }

   /**
   * Slug of the severity. You can get it in the System Field settings.
   * @return severity
  **/
  @javax.annotation.Nullable
  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }


  public ResultCreateCase priority(String priority) {
    this.priority = priority;
    return this;
  }

   /**
   * Slug of the priority. You can get it in the System Field settings.
   * @return priority
  **/
  @javax.annotation.Nullable
  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
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
   * @return the ResultCreateCase instance itself
   */
  public ResultCreateCase putAdditionalProperty(String key, Object value) {
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
    ResultCreateCase resultCreateCase = (ResultCreateCase) o;
    return Objects.equals(this.title, resultCreateCase.title) &&
        Objects.equals(this.suiteTitle, resultCreateCase.suiteTitle) &&
        Objects.equals(this.description, resultCreateCase.description) &&
        Objects.equals(this.preconditions, resultCreateCase.preconditions) &&
        Objects.equals(this.postconditions, resultCreateCase.postconditions) &&
        Objects.equals(this.layer, resultCreateCase.layer) &&
        Objects.equals(this.severity, resultCreateCase.severity) &&
        Objects.equals(this.priority, resultCreateCase.priority)&&
        Objects.equals(this.additionalProperties, resultCreateCase.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, suiteTitle, description, preconditions, postconditions, layer, severity, priority, additionalProperties);
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
    sb.append("class ResultCreateCase {\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    suiteTitle: ").append(toIndentedString(suiteTitle)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    preconditions: ").append(toIndentedString(preconditions)).append("\n");
    sb.append("    postconditions: ").append(toIndentedString(postconditions)).append("\n");
    sb.append("    layer: ").append(toIndentedString(layer)).append("\n");
    sb.append("    severity: ").append(toIndentedString(severity)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
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
    openapiFields.add("suite_title");
    openapiFields.add("description");
    openapiFields.add("preconditions");
    openapiFields.add("postconditions");
    openapiFields.add("layer");
    openapiFields.add("severity");
    openapiFields.add("priority");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ResultCreateCase
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ResultCreateCase.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ResultCreateCase is not found in the empty JSON string", ResultCreateCase.openapiRequiredFields.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("title") != null && !jsonObj.get("title").isJsonNull()) && !jsonObj.get("title").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj.get("title").toString()));
      }
      if ((jsonObj.get("suite_title") != null && !jsonObj.get("suite_title").isJsonNull()) && !jsonObj.get("suite_title").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `suite_title` to be a primitive type in the JSON string but got `%s`", jsonObj.get("suite_title").toString()));
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
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ResultCreateCase.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ResultCreateCase' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ResultCreateCase> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ResultCreateCase.class));

       return (TypeAdapter<T>) new TypeAdapter<ResultCreateCase>() {
           @Override
           public void write(JsonWriter out, ResultCreateCase value) throws IOException {
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
           public ResultCreateCase read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             ResultCreateCase instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of ResultCreateCase given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ResultCreateCase
  * @throws IOException if the JSON string is invalid with respect to ResultCreateCase
  */
  public static ResultCreateCase fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ResultCreateCase.class);
  }

 /**
  * Convert an instance of ResultCreateCase to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

