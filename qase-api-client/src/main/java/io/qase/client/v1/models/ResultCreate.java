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
import io.qase.client.v1.models.ResultCreateCase;
import io.qase.client.v1.models.TestStepResultCreate;
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
 * ResultCreate
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-10-07T13:46:56.402996677Z[Etc/UTC]", comments = "Generator version: 7.4.0")
public class ResultCreate {
  public static final String SERIALIZED_NAME_CASE_ID = "case_id";
  @SerializedName(SERIALIZED_NAME_CASE_ID)
  private Long caseId;

  public static final String SERIALIZED_NAME_CASE = "case";
  @SerializedName(SERIALIZED_NAME_CASE)
  private ResultCreateCase _case;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private String status;

  public static final String SERIALIZED_NAME_START_TIME = "start_time";
  @SerializedName(SERIALIZED_NAME_START_TIME)
  private Integer startTime;

  public static final String SERIALIZED_NAME_TIME = "time";
  @SerializedName(SERIALIZED_NAME_TIME)
  private Long time;

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

  public static final String SERIALIZED_NAME_PARAM = "param";
  @SerializedName(SERIALIZED_NAME_PARAM)
  private Map<String, String> param;

  public static final String SERIALIZED_NAME_PARAM_GROUPS = "param_groups";
  @SerializedName(SERIALIZED_NAME_PARAM_GROUPS)
  private List<List<String>> paramGroups;

  public static final String SERIALIZED_NAME_STEPS = "steps";
  @SerializedName(SERIALIZED_NAME_STEPS)
  private List<TestStepResultCreate> steps;

  public static final String SERIALIZED_NAME_AUTHOR_ID = "author_id";
  @SerializedName(SERIALIZED_NAME_AUTHOR_ID)
  private Long authorId;

  public ResultCreate() {
  }

  public ResultCreate caseId(Long caseId) {
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


  public ResultCreate _case(ResultCreateCase _case) {
    this._case = _case;
    return this;
  }

   /**
   * Get _case
   * @return _case
  **/
  @javax.annotation.Nullable
  public ResultCreateCase getCase() {
    return _case;
  }

  public void setCase(ResultCreateCase _case) {
    this._case = _case;
  }


  public ResultCreate status(String status) {
    this.status = status;
    return this;
  }

   /**
   * Can have the following values &#x60;passed&#x60;, &#x60;failed&#x60;, &#x60;blocked&#x60;, &#x60;skipped&#x60;, &#x60;invalid&#x60; + custom statuses
   * @return status
  **/
  @javax.annotation.Nonnull
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


  public ResultCreate startTime(Integer startTime) {
    this.startTime = startTime;
    return this;
  }

   /**
   * Get startTime
   * minimum: 0
   * @return startTime
  **/
  @javax.annotation.Nullable
  public Integer getStartTime() {
    return startTime;
  }

  public void setStartTime(Integer startTime) {
    this.startTime = startTime;
  }


  public ResultCreate time(Long time) {
    this.time = time;
    return this;
  }

   /**
   * Get time
   * minimum: 0
   * maximum: 31536000
   * @return time
  **/
  @javax.annotation.Nullable
  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }


  public ResultCreate timeMs(Long timeMs) {
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


  public ResultCreate defect(Boolean defect) {
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


  public ResultCreate attachments(List<String> attachments) {
    this.attachments = attachments;
    return this;
  }

  public ResultCreate addAttachmentsItem(String attachmentsItem) {
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


  public ResultCreate stacktrace(String stacktrace) {
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


  public ResultCreate comment(String comment) {
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


  public ResultCreate param(Map<String, String> param) {
    this.param = param;
    return this;
  }

  public ResultCreate putParamItem(String key, String paramItem) {
    if (this.param == null) {
      this.param = new HashMap<>();
    }
    this.param.put(key, paramItem);
    return this;
  }

   /**
   * A map of parameters (name &#x3D;&gt; value)
   * @return param
  **/
  @javax.annotation.Nullable
  public Map<String, String> getParam() {
    return param;
  }

  public void setParam(Map<String, String> param) {
    this.param = param;
  }


  public ResultCreate paramGroups(List<List<String>> paramGroups) {
    this.paramGroups = paramGroups;
    return this;
  }

  public ResultCreate addParamGroupsItem(List<String> paramGroupsItem) {
    if (this.paramGroups == null) {
      this.paramGroups = new ArrayList<>();
    }
    this.paramGroups.add(paramGroupsItem);
    return this;
  }

   /**
   * List parameter groups by name only. Add their values in the &#39;param&#39; field
   * @return paramGroups
  **/
  @javax.annotation.Nullable
  public List<List<String>> getParamGroups() {
    return paramGroups;
  }

  public void setParamGroups(List<List<String>> paramGroups) {
    this.paramGroups = paramGroups;
  }


  public ResultCreate steps(List<TestStepResultCreate> steps) {
    this.steps = steps;
    return this;
  }

  public ResultCreate addStepsItem(TestStepResultCreate stepsItem) {
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


  public ResultCreate authorId(Long authorId) {
    this.authorId = authorId;
    return this;
  }

   /**
   * Get authorId
   * @return authorId
  **/
  @javax.annotation.Nullable
  public Long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResultCreate resultCreate = (ResultCreate) o;
    return Objects.equals(this.caseId, resultCreate.caseId) &&
        Objects.equals(this._case, resultCreate._case) &&
        Objects.equals(this.status, resultCreate.status) &&
        Objects.equals(this.startTime, resultCreate.startTime) &&
        Objects.equals(this.time, resultCreate.time) &&
        Objects.equals(this.timeMs, resultCreate.timeMs) &&
        Objects.equals(this.defect, resultCreate.defect) &&
        Objects.equals(this.attachments, resultCreate.attachments) &&
        Objects.equals(this.stacktrace, resultCreate.stacktrace) &&
        Objects.equals(this.comment, resultCreate.comment) &&
        Objects.equals(this.param, resultCreate.param) &&
        Objects.equals(this.paramGroups, resultCreate.paramGroups) &&
        Objects.equals(this.steps, resultCreate.steps) &&
        Objects.equals(this.authorId, resultCreate.authorId);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(caseId, _case, status, startTime, time, timeMs, defect, attachments, stacktrace, comment, param, paramGroups, steps, authorId);
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
    sb.append("class ResultCreate {\n");
    sb.append("    caseId: ").append(toIndentedString(caseId)).append("\n");
    sb.append("    _case: ").append(toIndentedString(_case)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    timeMs: ").append(toIndentedString(timeMs)).append("\n");
    sb.append("    defect: ").append(toIndentedString(defect)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
    sb.append("    stacktrace: ").append(toIndentedString(stacktrace)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    param: ").append(toIndentedString(param)).append("\n");
    sb.append("    paramGroups: ").append(toIndentedString(paramGroups)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
    sb.append("    authorId: ").append(toIndentedString(authorId)).append("\n");
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
    openapiFields.add("case_id");
    openapiFields.add("case");
    openapiFields.add("status");
    openapiFields.add("start_time");
    openapiFields.add("time");
    openapiFields.add("time_ms");
    openapiFields.add("defect");
    openapiFields.add("attachments");
    openapiFields.add("stacktrace");
    openapiFields.add("comment");
    openapiFields.add("param");
    openapiFields.add("param_groups");
    openapiFields.add("steps");
    openapiFields.add("author_id");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("status");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ResultCreate
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ResultCreate.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ResultCreate is not found in the empty JSON string", ResultCreate.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!ResultCreate.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ResultCreate` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ResultCreate.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // validate the optional field `case`
      if (jsonObj.get("case") != null && !jsonObj.get("case").isJsonNull()) {
        ResultCreateCase.validateJsonElement(jsonObj.get("case"));
      }
      if (!jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
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
      // ensure the optional json data is an array if present
      if (jsonObj.get("param_groups") != null && !jsonObj.get("param_groups").isJsonNull() && !jsonObj.get("param_groups").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `param_groups` to be an array in the JSON string but got `%s`", jsonObj.get("param_groups").toString()));
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
       if (!ResultCreate.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ResultCreate' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ResultCreate> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ResultCreate.class));

       return (TypeAdapter<T>) new TypeAdapter<ResultCreate>() {
           @Override
           public void write(JsonWriter out, ResultCreate value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ResultCreate read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ResultCreate given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ResultCreate
  * @throws IOException if the JSON string is invalid with respect to ResultCreate
  */
  public static ResultCreate fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ResultCreate.class);
  }

 /**
  * Convert an instance of ResultCreate to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
