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
import io.qase.client.v1.models.TestStepResultCreate;
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
 * ResultUpdate
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-10-07T13:46:56.402996677Z[Etc/UTC]", comments = "Generator version: 7.4.0")
public class ResultUpdate {
  /**
   * Gets or Sets status
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    IN_PROGRESS("in_progress"),
    
    PASSED("passed"),
    
    FAILED("failed"),
    
    BLOCKED("blocked"),
    
    SKIPPED("skipped");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StatusEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      StatusEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private StatusEnum status;

  public static final String SERIALIZED_NAME_TIME_MS = "time_ms";
  @SerializedName(SERIALIZED_NAME_TIME_MS)
  private Long timeMs;

  public static final String SERIALIZED_NAME_DEFECT = "defect";
  @SerializedName(SERIALIZED_NAME_DEFECT)
  private Boolean defect;

  public static final String SERIALIZED_NAME_ATTACHMENTS = "attachments";
  @SerializedName(SERIALIZED_NAME_ATTACHMENTS)
  private List<String> attachments;

  public static final String SERIALIZED_NAME_STACKTRACE = "stacktrace";
  @SerializedName(SERIALIZED_NAME_STACKTRACE)
  private String stacktrace;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_STEPS = "steps";
  @SerializedName(SERIALIZED_NAME_STEPS)
  private List<TestStepResultCreate> steps;

  public ResultUpdate() {
  }

  public ResultUpdate status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @javax.annotation.Nullable
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public ResultUpdate timeMs(Long timeMs) {
    this.timeMs = timeMs;
    return this;
  }

   /**
   * Get timeMs
   * minimum: 0
   * maximum: 31536000000
   * @return timeMs
  **/
  @javax.annotation.Nullable
  public Long getTimeMs() {
    return timeMs;
  }

  public void setTimeMs(Long timeMs) {
    this.timeMs = timeMs;
  }


  public ResultUpdate defect(Boolean defect) {
    this.defect = defect;
    return this;
  }

   /**
   * Get defect
   * @return defect
  **/
  @javax.annotation.Nullable
  public Boolean getDefect() {
    return defect;
  }

  public void setDefect(Boolean defect) {
    this.defect = defect;
  }


  public ResultUpdate attachments(List<String> attachments) {
    this.attachments = attachments;
    return this;
  }

  public ResultUpdate addAttachmentsItem(String attachmentsItem) {
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
  public List<String> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<String> attachments) {
    this.attachments = attachments;
  }


  public ResultUpdate stacktrace(String stacktrace) {
    this.stacktrace = stacktrace;
    return this;
  }

   /**
   * Get stacktrace
   * @return stacktrace
  **/
  @javax.annotation.Nullable
  public String getStacktrace() {
    return stacktrace;
  }

  public void setStacktrace(String stacktrace) {
    this.stacktrace = stacktrace;
  }


  public ResultUpdate comment(String comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Get comment
   * @return comment
  **/
  @javax.annotation.Nullable
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }


  public ResultUpdate steps(List<TestStepResultCreate> steps) {
    this.steps = steps;
    return this;
  }

  public ResultUpdate addStepsItem(TestStepResultCreate stepsItem) {
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
  public List<TestStepResultCreate> getSteps() {
    return steps;
  }

  public void setSteps(List<TestStepResultCreate> steps) {
    this.steps = steps;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResultUpdate resultUpdate = (ResultUpdate) o;
    return Objects.equals(this.status, resultUpdate.status) &&
        Objects.equals(this.timeMs, resultUpdate.timeMs) &&
        Objects.equals(this.defect, resultUpdate.defect) &&
        Objects.equals(this.attachments, resultUpdate.attachments) &&
        Objects.equals(this.stacktrace, resultUpdate.stacktrace) &&
        Objects.equals(this.comment, resultUpdate.comment) &&
        Objects.equals(this.steps, resultUpdate.steps);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, timeMs, defect, attachments, stacktrace, comment, steps);
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
    sb.append("class ResultUpdate {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    timeMs: ").append(toIndentedString(timeMs)).append("\n");
    sb.append("    defect: ").append(toIndentedString(defect)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
    sb.append("    stacktrace: ").append(toIndentedString(stacktrace)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
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
    openapiFields.add("status");
    openapiFields.add("time_ms");
    openapiFields.add("defect");
    openapiFields.add("attachments");
    openapiFields.add("stacktrace");
    openapiFields.add("comment");
    openapiFields.add("steps");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ResultUpdate
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ResultUpdate.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ResultUpdate is not found in the empty JSON string", ResultUpdate.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!ResultUpdate.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ResultUpdate` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("status") != null && !jsonObj.get("status").isJsonNull()) && !jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      // validate the optional field `status`
      if (jsonObj.get("status") != null && !jsonObj.get("status").isJsonNull()) {
        StatusEnum.validateJsonElement(jsonObj.get("status"));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("attachments") != null && !jsonObj.get("attachments").isJsonNull() && !jsonObj.get("attachments").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `attachments` to be an array in the JSON string but got `%s`", jsonObj.get("attachments").toString()));
      }
      if ((jsonObj.get("stacktrace") != null && !jsonObj.get("stacktrace").isJsonNull()) && !jsonObj.get("stacktrace").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `stacktrace` to be a primitive type in the JSON string but got `%s`", jsonObj.get("stacktrace").toString()));
      }
      if ((jsonObj.get("comment") != null && !jsonObj.get("comment").isJsonNull()) && !jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
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
            TestStepResultCreate.validateJsonElement(jsonArraysteps.get(i));
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ResultUpdate.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ResultUpdate' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ResultUpdate> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ResultUpdate.class));

       return (TypeAdapter<T>) new TypeAdapter<ResultUpdate>() {
           @Override
           public void write(JsonWriter out, ResultUpdate value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ResultUpdate read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ResultUpdate given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ResultUpdate
  * @throws IOException if the JSON string is invalid with respect to ResultUpdate
  */
  public static ResultUpdate fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ResultUpdate.class);
  }

 /**
  * Convert an instance of ResultUpdate to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
