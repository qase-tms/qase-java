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
import io.qase.client.v1.models.TestStepResult;
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
 * ResultQuery
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class ResultQuery {
  public static final String SERIALIZED_NAME_HASH = "hash";
  @SerializedName(SERIALIZED_NAME_HASH)
  private String hash;

  public static final String SERIALIZED_NAME_RESULT_HASH = "result_hash";
  @SerializedName(SERIALIZED_NAME_RESULT_HASH)
  private String resultHash;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_STACKTRACE = "stacktrace";
  @SerializedName(SERIALIZED_NAME_STACKTRACE)
  private String stacktrace;

  public static final String SERIALIZED_NAME_RUN_ID = "run_id";
  @SerializedName(SERIALIZED_NAME_RUN_ID)
  private Long runId;

  public static final String SERIALIZED_NAME_CASE_ID = "case_id";
  @SerializedName(SERIALIZED_NAME_CASE_ID)
  private Long caseId;

  public static final String SERIALIZED_NAME_STEPS = "steps";
  @SerializedName(SERIALIZED_NAME_STEPS)
  private List<TestStepResult> steps;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private String status;

  public static final String SERIALIZED_NAME_IS_API_RESULT = "is_api_result";
  @SerializedName(SERIALIZED_NAME_IS_API_RESULT)
  private Boolean isApiResult;

  public static final String SERIALIZED_NAME_TIME_SPENT_MS = "time_spent_ms";
  @SerializedName(SERIALIZED_NAME_TIME_SPENT_MS)
  private Long timeSpentMs;

  public static final String SERIALIZED_NAME_END_TIME = "end_time";
  @SerializedName(SERIALIZED_NAME_END_TIME)
  private OffsetDateTime endTime;

  public static final String SERIALIZED_NAME_ATTACHMENTS = "attachments";
  @SerializedName(SERIALIZED_NAME_ATTACHMENTS)
  private List<Attachment> attachments;

  public ResultQuery() {
  }

  public ResultQuery hash(String hash) {
    this.hash = hash;
    return this;
  }

   /**
   * Get hash
   * @return hash
  **/
  @javax.annotation.Nullable
  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }


  public ResultQuery resultHash(String resultHash) {
    this.resultHash = resultHash;
    return this;
  }

   /**
   * Get resultHash
   * @return resultHash
  **/
  @javax.annotation.Nonnull
  public String getResultHash() {
    return resultHash;
  }

  public void setResultHash(String resultHash) {
    this.resultHash = resultHash;
  }


  public ResultQuery comment(String comment) {
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


  public ResultQuery stacktrace(String stacktrace) {
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


  public ResultQuery runId(Long runId) {
    this.runId = runId;
    return this;
  }

   /**
   * Get runId
   * @return runId
  **/
  @javax.annotation.Nullable
  public Long getRunId() {
    return runId;
  }

  public void setRunId(Long runId) {
    this.runId = runId;
  }


  public ResultQuery caseId(Long caseId) {
    this.caseId = caseId;
    return this;
  }

   /**
   * Get caseId
   * @return caseId
  **/
  @javax.annotation.Nullable
  public Long getCaseId() {
    return caseId;
  }

  public void setCaseId(Long caseId) {
    this.caseId = caseId;
  }


  public ResultQuery steps(List<TestStepResult> steps) {
    this.steps = steps;
    return this;
  }

  public ResultQuery addStepsItem(TestStepResult stepsItem) {
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
  public List<TestStepResult> getSteps() {
    return steps;
  }

  public void setSteps(List<TestStepResult> steps) {
    this.steps = steps;
  }


  public ResultQuery status(String status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @javax.annotation.Nullable
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public ResultQuery isApiResult(Boolean isApiResult) {
    this.isApiResult = isApiResult;
    return this;
  }

   /**
   * Get isApiResult
   * @return isApiResult
  **/
  @javax.annotation.Nullable
  public Boolean getIsApiResult() {
    return isApiResult;
  }

  public void setIsApiResult(Boolean isApiResult) {
    this.isApiResult = isApiResult;
  }


  public ResultQuery timeSpentMs(Long timeSpentMs) {
    this.timeSpentMs = timeSpentMs;
    return this;
  }

   /**
   * Get timeSpentMs
   * @return timeSpentMs
  **/
  @javax.annotation.Nullable
  public Long getTimeSpentMs() {
    return timeSpentMs;
  }

  public void setTimeSpentMs(Long timeSpentMs) {
    this.timeSpentMs = timeSpentMs;
  }


  public ResultQuery endTime(OffsetDateTime endTime) {
    this.endTime = endTime;
    return this;
  }

   /**
   * Get endTime
   * @return endTime
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(OffsetDateTime endTime) {
    this.endTime = endTime;
  }


  public ResultQuery attachments(List<Attachment> attachments) {
    this.attachments = attachments;
    return this;
  }

  public ResultQuery addAttachmentsItem(Attachment attachmentsItem) {
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



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResultQuery resultQuery = (ResultQuery) o;
    return Objects.equals(this.hash, resultQuery.hash) &&
        Objects.equals(this.resultHash, resultQuery.resultHash) &&
        Objects.equals(this.comment, resultQuery.comment) &&
        Objects.equals(this.stacktrace, resultQuery.stacktrace) &&
        Objects.equals(this.runId, resultQuery.runId) &&
        Objects.equals(this.caseId, resultQuery.caseId) &&
        Objects.equals(this.steps, resultQuery.steps) &&
        Objects.equals(this.status, resultQuery.status) &&
        Objects.equals(this.isApiResult, resultQuery.isApiResult) &&
        Objects.equals(this.timeSpentMs, resultQuery.timeSpentMs) &&
        Objects.equals(this.endTime, resultQuery.endTime) &&
        Objects.equals(this.attachments, resultQuery.attachments);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(hash, resultHash, comment, stacktrace, runId, caseId, steps, status, isApiResult, timeSpentMs, endTime, attachments);
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
    sb.append("class ResultQuery {\n");
    sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
    sb.append("    resultHash: ").append(toIndentedString(resultHash)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    stacktrace: ").append(toIndentedString(stacktrace)).append("\n");
    sb.append("    runId: ").append(toIndentedString(runId)).append("\n");
    sb.append("    caseId: ").append(toIndentedString(caseId)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    isApiResult: ").append(toIndentedString(isApiResult)).append("\n");
    sb.append("    timeSpentMs: ").append(toIndentedString(timeSpentMs)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
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
    openapiFields.add("result_hash");
    openapiFields.add("comment");
    openapiFields.add("stacktrace");
    openapiFields.add("run_id");
    openapiFields.add("case_id");
    openapiFields.add("steps");
    openapiFields.add("status");
    openapiFields.add("is_api_result");
    openapiFields.add("time_spent_ms");
    openapiFields.add("end_time");
    openapiFields.add("attachments");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("result_hash");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ResultQuery
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ResultQuery.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ResultQuery is not found in the empty JSON string", ResultQuery.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!ResultQuery.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ResultQuery` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ResultQuery.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("hash") != null && !jsonObj.get("hash").isJsonNull()) && !jsonObj.get("hash").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `hash` to be a primitive type in the JSON string but got `%s`", jsonObj.get("hash").toString()));
      }
      if (!jsonObj.get("result_hash").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `result_hash` to be a primitive type in the JSON string but got `%s`", jsonObj.get("result_hash").toString()));
      }
      if ((jsonObj.get("comment") != null && !jsonObj.get("comment").isJsonNull()) && !jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      if ((jsonObj.get("stacktrace") != null && !jsonObj.get("stacktrace").isJsonNull()) && !jsonObj.get("stacktrace").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `stacktrace` to be a primitive type in the JSON string but got `%s`", jsonObj.get("stacktrace").toString()));
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
            TestStepResult.validateJsonElement(jsonArraysteps.get(i));
          };
        }
      }
      if ((jsonObj.get("status") != null && !jsonObj.get("status").isJsonNull()) && !jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
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
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ResultQuery.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ResultQuery' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ResultQuery> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ResultQuery.class));

       return (TypeAdapter<T>) new TypeAdapter<ResultQuery>() {
           @Override
           public void write(JsonWriter out, ResultQuery value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ResultQuery read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ResultQuery given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ResultQuery
  * @throws IOException if the JSON string is invalid with respect to ResultQuery
  */
  public static ResultQuery fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ResultQuery.class);
  }

 /**
  * Convert an instance of ResultQuery to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

