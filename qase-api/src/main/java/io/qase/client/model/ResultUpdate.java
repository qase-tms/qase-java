/*
 * Qase.io API
 * # Introduction  You can use our API to access [Qase.io](https://qase.io) API endpoints, which allows to retrieve information about entities stored in database and perform actions with them. The API is organized around [REST](http://en.wikipedia.org/wiki/Representational_State_Transfer).  # API Rate limits  Your application can make up to 200 API requests per minute.  Once the limit is exceeded, clients receive an HTTP 429 with a Retry-After: X header to indicate how long their timeout period is before they will be able to send requests again. The timeout period is set to 60 seconds once the limit is exceeded.  # Authentication  To authorize, use this code:  ```shell # With shell, you can just pass the correct header with each request curl \"https://api.qase.io/v1/api_endpoint\"   -H \"Token: api_token\"   -H \"Content-Type: application/json\" ```  Make sure to replace `api_token` with your API key.  Qase.io uses API tokens to authenticate requests. You can view an manage your API keys in [API tokens pages](https://app.qase.io/user/api/token).  Your API keys has the same access rights as your role in the app, so be sure to keep them secure! Do not share your secret API keys in publicly accessible areas such as GitHub, client-side code, and so forth.  Qase API expects for the API key to be included in all API requests to the server in a header that looks like the following:  `Token: api_token`  You must replace `api_token` with your personal API key.  All API requests must be made over [HTTPS](http://en.wikipedia.org/wiki/HTTP_Secure). Calls made over plain HTTP will fail. API requests without authentication will also fail.  # Access rights  Qase.io is using Role-based Access Control system to restrict some features usage in Web interface and the same rules are applied to API methods. In description for each method you will find a rule name, that is required to perform an action through API. If you don't have enough access rights, you will receive an error with `403` status code.  # Errors  Qase API uses the following error codes:  Code | Meaning ---------- | ------- 400 | Bad Request - Your request is invalid. 401 | Unauthorized - Your API key is wrong. 403 | Forbidden - Your role doesn't have enough permissions to perform this action 404 | Not Found - The resource could not be found. 405 | Method Not Allowed - You tried to access a resource with an invalid method. 406 | Not Acceptable - You requested a format that isn't json. 410 | Gone - The resource requested has been removed from our servers. 429 | Too Many Requests - You're performing too many requests! Slow down! 500 | Internal Server Error - We had a problem with our server. Try again later. 503 | Service Unavailable - We're temporarily offline for maintenance. Please try again later. 
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: support@qase.io
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.qase.client.model;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * ResultUpdate
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-15T23:17:33.666847+03:00[Europe/Moscow]")
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
  private List<String> attachments = null;

  public static final String SERIALIZED_NAME_STACKTRACE = "stacktrace";
  @SerializedName(SERIALIZED_NAME_STACKTRACE)
  private String stacktrace;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_STEPS = "steps";
  @SerializedName(SERIALIZED_NAME_STEPS)
  private List<ResultUpdateSteps> steps = null;


  public ResultUpdate status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

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
  @ApiModelProperty(value = "")

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
  @ApiModelProperty(value = "")

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
  @ApiModelProperty(value = "")

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
  @ApiModelProperty(value = "")

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
  @ApiModelProperty(value = "")

  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public ResultUpdate steps(List<ResultUpdateSteps> steps) {
    
    this.steps = steps;
    return this;
  }

  public ResultUpdate addStepsItem(ResultUpdateSteps stepsItem) {
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
  @ApiModelProperty(value = "")

  public List<ResultUpdateSteps> getSteps() {
    return steps;
  }


  public void setSteps(List<ResultUpdateSteps> steps) {
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

}
