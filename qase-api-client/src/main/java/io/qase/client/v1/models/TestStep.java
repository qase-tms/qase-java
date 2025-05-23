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
import io.qase.client.v1.models.Attachment;
import java.io.IOException;
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
 * TestStep
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class TestStep {
  public static final String SERIALIZED_NAME_HASH = "hash";
  @Deprecated
  @SerializedName(SERIALIZED_NAME_HASH)
  private String hash;

  public static final String SERIALIZED_NAME_SHARED_STEP_HASH = "shared_step_hash";
  @SerializedName(SERIALIZED_NAME_SHARED_STEP_HASH)
  private String sharedStepHash;

  public static final String SERIALIZED_NAME_SHARED_STEP_NESTED_HASH = "shared_step_nested_hash";
  @SerializedName(SERIALIZED_NAME_SHARED_STEP_NESTED_HASH)
  private String sharedStepNestedHash;

  public static final String SERIALIZED_NAME_POSITION = "position";
  @Deprecated
  @SerializedName(SERIALIZED_NAME_POSITION)
  private Integer position;

  public static final String SERIALIZED_NAME_ACTION = "action";
  @SerializedName(SERIALIZED_NAME_ACTION)
  private String action;

  public static final String SERIALIZED_NAME_EXPECTED_RESULT = "expected_result";
  @SerializedName(SERIALIZED_NAME_EXPECTED_RESULT)
  private String expectedResult;

  public static final String SERIALIZED_NAME_DATA = "data";
  @SerializedName(SERIALIZED_NAME_DATA)
  private String data;

  public static final String SERIALIZED_NAME_ATTACHMENTS = "attachments";
  @SerializedName(SERIALIZED_NAME_ATTACHMENTS)
  private List<Attachment> attachments;

  public static final String SERIALIZED_NAME_STEPS = "steps";
  @SerializedName(SERIALIZED_NAME_STEPS)
  private List<Object> steps;

  public TestStep() {
  }

  @Deprecated
  public TestStep hash(String hash) {
    this.hash = hash;
    return this;
  }

   /**
   * Get hash
   * @return hash
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  public String getHash() {
    return hash;
  }

  @Deprecated
  public void setHash(String hash) {
    this.hash = hash;
  }


  public TestStep sharedStepHash(String sharedStepHash) {
    this.sharedStepHash = sharedStepHash;
    return this;
  }

   /**
   * Get sharedStepHash
   * @return sharedStepHash
  **/
  @javax.annotation.Nullable
  public String getSharedStepHash() {
    return sharedStepHash;
  }

  public void setSharedStepHash(String sharedStepHash) {
    this.sharedStepHash = sharedStepHash;
  }


  public TestStep sharedStepNestedHash(String sharedStepNestedHash) {
    this.sharedStepNestedHash = sharedStepNestedHash;
    return this;
  }

   /**
   * Get sharedStepNestedHash
   * @return sharedStepNestedHash
  **/
  @javax.annotation.Nullable
  public String getSharedStepNestedHash() {
    return sharedStepNestedHash;
  }

  public void setSharedStepNestedHash(String sharedStepNestedHash) {
    this.sharedStepNestedHash = sharedStepNestedHash;
  }


  @Deprecated
  public TestStep position(Integer position) {
    this.position = position;
    return this;
  }

   /**
   * Get position
   * @return position
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  public Integer getPosition() {
    return position;
  }

  @Deprecated
  public void setPosition(Integer position) {
    this.position = position;
  }


  public TestStep action(String action) {
    this.action = action;
    return this;
  }

   /**
   * Get action
   * @return action
  **/
  @javax.annotation.Nullable
  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }


  public TestStep expectedResult(String expectedResult) {
    this.expectedResult = expectedResult;
    return this;
  }

   /**
   * Get expectedResult
   * @return expectedResult
  **/
  @javax.annotation.Nullable
  public String getExpectedResult() {
    return expectedResult;
  }

  public void setExpectedResult(String expectedResult) {
    this.expectedResult = expectedResult;
  }


  public TestStep data(String data) {
    this.data = data;
    return this;
  }

   /**
   * Get data
   * @return data
  **/
  @javax.annotation.Nullable
  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }


  public TestStep attachments(List<Attachment> attachments) {
    this.attachments = attachments;
    return this;
  }

  public TestStep addAttachmentsItem(Attachment attachmentsItem) {
    if (this.attachments == null) {
      this.attachments = new ArrayList<>();
    }
    this.attachments.add(attachmentsItem);
    return this;
  }

   /**
   * Get attachments
   * @return attachments
  **/
  @javax.annotation.Nullable
  public List<Attachment> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<Attachment> attachments) {
    this.attachments = attachments;
  }


  public TestStep steps(List<Object> steps) {
    this.steps = steps;
    return this;
  }

  public TestStep addStepsItem(Object stepsItem) {
    if (this.steps == null) {
      this.steps = new ArrayList<>();
    }
    this.steps.add(stepsItem);
    return this;
  }

   /**
   * Nested steps will be here. The same structure is used for them.
   * @return steps
  **/
  @javax.annotation.Nullable
  public List<Object> getSteps() {
    return steps;
  }

  public void setSteps(List<Object> steps) {
    this.steps = steps;
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
   * @return the TestStep instance itself
   */
  public TestStep putAdditionalProperty(String key, Object value) {
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
    TestStep testStep = (TestStep) o;
    return Objects.equals(this.hash, testStep.hash) &&
        Objects.equals(this.sharedStepHash, testStep.sharedStepHash) &&
        Objects.equals(this.sharedStepNestedHash, testStep.sharedStepNestedHash) &&
        Objects.equals(this.position, testStep.position) &&
        Objects.equals(this.action, testStep.action) &&
        Objects.equals(this.expectedResult, testStep.expectedResult) &&
        Objects.equals(this.data, testStep.data) &&
        Objects.equals(this.attachments, testStep.attachments) &&
        Objects.equals(this.steps, testStep.steps)&&
        Objects.equals(this.additionalProperties, testStep.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(hash, sharedStepHash, sharedStepNestedHash, position, action, expectedResult, data, attachments, steps, additionalProperties);
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
    sb.append("class TestStep {\n");
    sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
    sb.append("    sharedStepHash: ").append(toIndentedString(sharedStepHash)).append("\n");
    sb.append("    sharedStepNestedHash: ").append(toIndentedString(sharedStepNestedHash)).append("\n");
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    expectedResult: ").append(toIndentedString(expectedResult)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
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
    openapiFields.add("hash");
    openapiFields.add("shared_step_hash");
    openapiFields.add("shared_step_nested_hash");
    openapiFields.add("position");
    openapiFields.add("action");
    openapiFields.add("expected_result");
    openapiFields.add("data");
    openapiFields.add("attachments");
    openapiFields.add("steps");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to TestStep
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!TestStep.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in TestStep is not found in the empty JSON string", TestStep.openapiRequiredFields.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("hash") != null && !jsonObj.get("hash").isJsonNull()) && !jsonObj.get("hash").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `hash` to be a primitive type in the JSON string but got `%s`", jsonObj.get("hash").toString()));
      }
      if ((jsonObj.get("shared_step_hash") != null && !jsonObj.get("shared_step_hash").isJsonNull()) && !jsonObj.get("shared_step_hash").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `shared_step_hash` to be a primitive type in the JSON string but got `%s`", jsonObj.get("shared_step_hash").toString()));
      }
      if ((jsonObj.get("shared_step_nested_hash") != null && !jsonObj.get("shared_step_nested_hash").isJsonNull()) && !jsonObj.get("shared_step_nested_hash").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `shared_step_nested_hash` to be a primitive type in the JSON string but got `%s`", jsonObj.get("shared_step_nested_hash").toString()));
      }
      if ((jsonObj.get("action") != null && !jsonObj.get("action").isJsonNull()) && !jsonObj.get("action").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `action` to be a primitive type in the JSON string but got `%s`", jsonObj.get("action").toString()));
      }
      if ((jsonObj.get("expected_result") != null && !jsonObj.get("expected_result").isJsonNull()) && !jsonObj.get("expected_result").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `expected_result` to be a primitive type in the JSON string but got `%s`", jsonObj.get("expected_result").toString()));
      }
      if ((jsonObj.get("data") != null && !jsonObj.get("data").isJsonNull()) && !jsonObj.get("data").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `data` to be a primitive type in the JSON string but got `%s`", jsonObj.get("data").toString()));
      }
      if (jsonObj.get("attachments") != null && !jsonObj.get("attachments").isJsonNull()) {
        JsonArray jsonArrayattachments = jsonObj.getAsJsonArray("attachments");
        if (jsonArrayattachments != null) {
          // ensure the json data is an array
          if (!jsonObj.get("attachments").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `attachments` to be an array in the JSON string but got `%s`", jsonObj.get("attachments").toString()));
          }

          // validate the optional field `attachments` (array)
          for (int i = 0; i < jsonArrayattachments.size(); i++) {
            Attachment.validateJsonElement(jsonArrayattachments.get(i));
          };
        }
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("steps") != null && !jsonObj.get("steps").isJsonNull() && !jsonObj.get("steps").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `steps` to be an array in the JSON string but got `%s`", jsonObj.get("steps").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!TestStep.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'TestStep' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<TestStep> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(TestStep.class));

       return (TypeAdapter<T>) new TypeAdapter<TestStep>() {
           @Override
           public void write(JsonWriter out, TestStep value) throws IOException {
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
           public TestStep read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             TestStep instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of TestStep given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of TestStep
  * @throws IOException if the JSON string is invalid with respect to TestStep
  */
  public static TestStep fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, TestStep.class);
  }

 /**
  * Convert an instance of TestStep to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

