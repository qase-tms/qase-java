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
import io.qase.client.v1.models.CustomFieldValue;
import io.qase.client.v1.models.TagValue;
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
 * QqlDefect
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class QqlDefect {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Long id;

  public static final String SERIALIZED_NAME_DEFECT_ID = "defect_id";
  @SerializedName(SERIALIZED_NAME_DEFECT_ID)
  private Long defectId;

  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_ACTUAL_RESULT = "actual_result";
  @SerializedName(SERIALIZED_NAME_ACTUAL_RESULT)
  private String actualResult;

  public static final String SERIALIZED_NAME_SEVERITY = "severity";
  @SerializedName(SERIALIZED_NAME_SEVERITY)
  private String severity;

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private String status;

  public static final String SERIALIZED_NAME_MILESTONE_ID = "milestone_id";
  @SerializedName(SERIALIZED_NAME_MILESTONE_ID)
  private Long milestoneId;

  public static final String SERIALIZED_NAME_CUSTOM_FIELDS = "custom_fields";
  @SerializedName(SERIALIZED_NAME_CUSTOM_FIELDS)
  private List<CustomFieldValue> customFields;

  public static final String SERIALIZED_NAME_ATTACHMENTS = "attachments";
  @SerializedName(SERIALIZED_NAME_ATTACHMENTS)
  private List<Attachment> attachments;

  public static final String SERIALIZED_NAME_RESOLVED = "resolved";
  @SerializedName(SERIALIZED_NAME_RESOLVED)
  private OffsetDateTime resolved;

  public static final String SERIALIZED_NAME_MEMBER_ID = "member_id";
  @Deprecated
  @SerializedName(SERIALIZED_NAME_MEMBER_ID)
  private Long memberId;

  public static final String SERIALIZED_NAME_AUTHOR_ID = "author_id";
  @SerializedName(SERIALIZED_NAME_AUTHOR_ID)
  private Long authorId;

  public static final String SERIALIZED_NAME_EXTERNAL_DATA = "external_data";
  @SerializedName(SERIALIZED_NAME_EXTERNAL_DATA)
  private String externalData;

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<TagValue> tags;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private OffsetDateTime updatedAt;

  public QqlDefect() {
  }

  public QqlDefect id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public QqlDefect defectId(Long defectId) {
    this.defectId = defectId;
    return this;
  }

   /**
   * Get defectId
   * @return defectId
  **/
  @javax.annotation.Nonnull
  public Long getDefectId() {
    return defectId;
  }

  public void setDefectId(Long defectId) {
    this.defectId = defectId;
  }


  public QqlDefect title(String title) {
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


  public QqlDefect actualResult(String actualResult) {
    this.actualResult = actualResult;
    return this;
  }

   /**
   * Get actualResult
   * @return actualResult
  **/
  @javax.annotation.Nullable
  public String getActualResult() {
    return actualResult;
  }

  public void setActualResult(String actualResult) {
    this.actualResult = actualResult;
  }


  public QqlDefect severity(String severity) {
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


  public QqlDefect status(String status) {
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


  public QqlDefect milestoneId(Long milestoneId) {
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


  public QqlDefect customFields(List<CustomFieldValue> customFields) {
    this.customFields = customFields;
    return this;
  }

  public QqlDefect addCustomFieldsItem(CustomFieldValue customFieldsItem) {
    if (this.customFields == null) {
      this.customFields = new ArrayList<>();
    }
    this.customFields.add(customFieldsItem);
    return this;
  }

   /**
   * Get customFields
   * @return customFields
  **/
  @javax.annotation.Nullable
  public List<CustomFieldValue> getCustomFields() {
    return customFields;
  }

  public void setCustomFields(List<CustomFieldValue> customFields) {
    this.customFields = customFields;
  }


  public QqlDefect attachments(List<Attachment> attachments) {
    this.attachments = attachments;
    return this;
  }

  public QqlDefect addAttachmentsItem(Attachment attachmentsItem) {
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


  public QqlDefect resolved(OffsetDateTime resolved) {
    this.resolved = resolved;
    return this;
  }

   /**
   * Get resolved
   * @return resolved
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getResolved() {
    return resolved;
  }

  public void setResolved(OffsetDateTime resolved) {
    this.resolved = resolved;
  }


  @Deprecated
  public QqlDefect memberId(Long memberId) {
    this.memberId = memberId;
    return this;
  }

   /**
   * Deprecated, use &#x60;author_id&#x60; instead.
   * @return memberId
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  public Long getMemberId() {
    return memberId;
  }

  @Deprecated
  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }


  public QqlDefect authorId(Long authorId) {
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


  public QqlDefect externalData(String externalData) {
    this.externalData = externalData;
    return this;
  }

   /**
   * Get externalData
   * @return externalData
  **/
  @javax.annotation.Nullable
  public String getExternalData() {
    return externalData;
  }

  public void setExternalData(String externalData) {
    this.externalData = externalData;
  }


  public QqlDefect tags(List<TagValue> tags) {
    this.tags = tags;
    return this;
  }

  public QqlDefect addTagsItem(TagValue tagsItem) {
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
  public List<TagValue> getTags() {
    return tags;
  }

  public void setTags(List<TagValue> tags) {
    this.tags = tags;
  }


  public QqlDefect createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Get createdAt
   * @return createdAt
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }


  public QqlDefect updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Get updatedAt
   * @return updatedAt
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QqlDefect qqlDefect = (QqlDefect) o;
    return Objects.equals(this.id, qqlDefect.id) &&
        Objects.equals(this.defectId, qqlDefect.defectId) &&
        Objects.equals(this.title, qqlDefect.title) &&
        Objects.equals(this.actualResult, qqlDefect.actualResult) &&
        Objects.equals(this.severity, qqlDefect.severity) &&
        Objects.equals(this.status, qqlDefect.status) &&
        Objects.equals(this.milestoneId, qqlDefect.milestoneId) &&
        Objects.equals(this.customFields, qqlDefect.customFields) &&
        Objects.equals(this.attachments, qqlDefect.attachments) &&
        Objects.equals(this.resolved, qqlDefect.resolved) &&
        Objects.equals(this.memberId, qqlDefect.memberId) &&
        Objects.equals(this.authorId, qqlDefect.authorId) &&
        Objects.equals(this.externalData, qqlDefect.externalData) &&
        Objects.equals(this.tags, qqlDefect.tags) &&
        Objects.equals(this.createdAt, qqlDefect.createdAt) &&
        Objects.equals(this.updatedAt, qqlDefect.updatedAt);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, defectId, title, actualResult, severity, status, milestoneId, customFields, attachments, resolved, memberId, authorId, externalData, tags, createdAt, updatedAt);
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
    sb.append("class QqlDefect {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    defectId: ").append(toIndentedString(defectId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    actualResult: ").append(toIndentedString(actualResult)).append("\n");
    sb.append("    severity: ").append(toIndentedString(severity)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    milestoneId: ").append(toIndentedString(milestoneId)).append("\n");
    sb.append("    customFields: ").append(toIndentedString(customFields)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
    sb.append("    resolved: ").append(toIndentedString(resolved)).append("\n");
    sb.append("    memberId: ").append(toIndentedString(memberId)).append("\n");
    sb.append("    authorId: ").append(toIndentedString(authorId)).append("\n");
    sb.append("    externalData: ").append(toIndentedString(externalData)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
    openapiFields.add("id");
    openapiFields.add("defect_id");
    openapiFields.add("title");
    openapiFields.add("actual_result");
    openapiFields.add("severity");
    openapiFields.add("status");
    openapiFields.add("milestone_id");
    openapiFields.add("custom_fields");
    openapiFields.add("attachments");
    openapiFields.add("resolved");
    openapiFields.add("member_id");
    openapiFields.add("author_id");
    openapiFields.add("external_data");
    openapiFields.add("tags");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("defect_id");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to QqlDefect
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!QqlDefect.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in QqlDefect is not found in the empty JSON string", QqlDefect.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!QqlDefect.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `QqlDefect` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : QqlDefect.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("title") != null && !jsonObj.get("title").isJsonNull()) && !jsonObj.get("title").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj.get("title").toString()));
      }
      if ((jsonObj.get("actual_result") != null && !jsonObj.get("actual_result").isJsonNull()) && !jsonObj.get("actual_result").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `actual_result` to be a primitive type in the JSON string but got `%s`", jsonObj.get("actual_result").toString()));
      }
      if ((jsonObj.get("severity") != null && !jsonObj.get("severity").isJsonNull()) && !jsonObj.get("severity").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `severity` to be a primitive type in the JSON string but got `%s`", jsonObj.get("severity").toString()));
      }
      if ((jsonObj.get("status") != null && !jsonObj.get("status").isJsonNull()) && !jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      if (jsonObj.get("custom_fields") != null && !jsonObj.get("custom_fields").isJsonNull()) {
        JsonArray jsonArraycustomFields = jsonObj.getAsJsonArray("custom_fields");
        if (jsonArraycustomFields != null) {
          // ensure the json data is an array
          if (!jsonObj.get("custom_fields").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `custom_fields` to be an array in the JSON string but got `%s`", jsonObj.get("custom_fields").toString()));
          }

          // validate the optional field `custom_fields` (array)
          for (int i = 0; i < jsonArraycustomFields.size(); i++) {
            CustomFieldValue.validateJsonElement(jsonArraycustomFields.get(i));
          };
        }
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
      if ((jsonObj.get("external_data") != null && !jsonObj.get("external_data").isJsonNull()) && !jsonObj.get("external_data").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `external_data` to be a primitive type in the JSON string but got `%s`", jsonObj.get("external_data").toString()));
      }
      if (jsonObj.get("tags") != null && !jsonObj.get("tags").isJsonNull()) {
        JsonArray jsonArraytags = jsonObj.getAsJsonArray("tags");
        if (jsonArraytags != null) {
          // ensure the json data is an array
          if (!jsonObj.get("tags").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `tags` to be an array in the JSON string but got `%s`", jsonObj.get("tags").toString()));
          }

          // validate the optional field `tags` (array)
          for (int i = 0; i < jsonArraytags.size(); i++) {
            TagValue.validateJsonElement(jsonArraytags.get(i));
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!QqlDefect.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'QqlDefect' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<QqlDefect> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(QqlDefect.class));

       return (TypeAdapter<T>) new TypeAdapter<QqlDefect>() {
           @Override
           public void write(JsonWriter out, QqlDefect value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public QqlDefect read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of QqlDefect given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of QqlDefect
  * @throws IOException if the JSON string is invalid with respect to QqlDefect
  */
  public static QqlDefect fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, QqlDefect.class);
  }

 /**
  * Convert an instance of QqlDefect to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

