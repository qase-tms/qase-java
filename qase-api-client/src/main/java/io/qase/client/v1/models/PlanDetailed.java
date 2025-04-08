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
import io.qase.client.v1.models.PlanDetailedAllOfCases;
import java.io.IOException;
import java.math.BigDecimal;
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
 * PlanDetailed
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class PlanDetailed {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Long id;

  public static final String SERIALIZED_NAME_TITLE = "title";
  @SerializedName(SERIALIZED_NAME_TITLE)
  private String title;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_CASES_COUNT = "cases_count";
  @SerializedName(SERIALIZED_NAME_CASES_COUNT)
  private Integer casesCount;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private OffsetDateTime updatedAt;

  public static final String SERIALIZED_NAME_CREATED = "created";
  @Deprecated
  @SerializedName(SERIALIZED_NAME_CREATED)
  private String created;

  public static final String SERIALIZED_NAME_UPDATED = "updated";
  @Deprecated
  @SerializedName(SERIALIZED_NAME_UPDATED)
  private String updated;

  public static final String SERIALIZED_NAME_AVERAGE_TIME = "average_time";
  @SerializedName(SERIALIZED_NAME_AVERAGE_TIME)
  private BigDecimal averageTime;

  public static final String SERIALIZED_NAME_CASES = "cases";
  @SerializedName(SERIALIZED_NAME_CASES)
  private List<PlanDetailedAllOfCases> cases;

  public PlanDetailed() {
  }

  public PlanDetailed id(Long id) {
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


  public PlanDetailed title(String title) {
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


  public PlanDetailed description(String description) {
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


  public PlanDetailed casesCount(Integer casesCount) {
    this.casesCount = casesCount;
    return this;
  }

   /**
   * Get casesCount
   * @return casesCount
  **/
  @javax.annotation.Nullable
  public Integer getCasesCount() {
    return casesCount;
  }

  public void setCasesCount(Integer casesCount) {
    this.casesCount = casesCount;
  }


  public PlanDetailed createdAt(OffsetDateTime createdAt) {
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


  public PlanDetailed updatedAt(OffsetDateTime updatedAt) {
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


  @Deprecated
  public PlanDetailed created(String created) {
    this.created = created;
    return this;
  }

   /**
   * Deprecated, use the &#x60;created_at&#x60; property instead.
   * @return created
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  public String getCreated() {
    return created;
  }

  @Deprecated
  public void setCreated(String created) {
    this.created = created;
  }


  @Deprecated
  public PlanDetailed updated(String updated) {
    this.updated = updated;
    return this;
  }

   /**
   * Deprecated, use the &#x60;updated_at&#x60; property instead.
   * @return updated
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  public String getUpdated() {
    return updated;
  }

  @Deprecated
  public void setUpdated(String updated) {
    this.updated = updated;
  }


  public PlanDetailed averageTime(BigDecimal averageTime) {
    this.averageTime = averageTime;
    return this;
  }

   /**
   * Get averageTime
   * @return averageTime
  **/
  @javax.annotation.Nullable
  public BigDecimal getAverageTime() {
    return averageTime;
  }

  public void setAverageTime(BigDecimal averageTime) {
    this.averageTime = averageTime;
  }


  public PlanDetailed cases(List<PlanDetailedAllOfCases> cases) {
    this.cases = cases;
    return this;
  }

  public PlanDetailed addCasesItem(PlanDetailedAllOfCases casesItem) {
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
  public List<PlanDetailedAllOfCases> getCases() {
    return cases;
  }

  public void setCases(List<PlanDetailedAllOfCases> cases) {
    this.cases = cases;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlanDetailed planDetailed = (PlanDetailed) o;
    return Objects.equals(this.id, planDetailed.id) &&
        Objects.equals(this.title, planDetailed.title) &&
        Objects.equals(this.description, planDetailed.description) &&
        Objects.equals(this.casesCount, planDetailed.casesCount) &&
        Objects.equals(this.createdAt, planDetailed.createdAt) &&
        Objects.equals(this.updatedAt, planDetailed.updatedAt) &&
        Objects.equals(this.created, planDetailed.created) &&
        Objects.equals(this.updated, planDetailed.updated) &&
        Objects.equals(this.averageTime, planDetailed.averageTime) &&
        Objects.equals(this.cases, planDetailed.cases);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, casesCount, createdAt, updatedAt, created, updated, averageTime, cases);
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
    sb.append("class PlanDetailed {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    casesCount: ").append(toIndentedString(casesCount)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    updated: ").append(toIndentedString(updated)).append("\n");
    sb.append("    averageTime: ").append(toIndentedString(averageTime)).append("\n");
    sb.append("    cases: ").append(toIndentedString(cases)).append("\n");
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
    openapiFields.add("title");
    openapiFields.add("description");
    openapiFields.add("cases_count");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");
    openapiFields.add("created");
    openapiFields.add("updated");
    openapiFields.add("average_time");
    openapiFields.add("cases");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to PlanDetailed
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!PlanDetailed.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in PlanDetailed is not found in the empty JSON string", PlanDetailed.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!PlanDetailed.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `PlanDetailed` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("title") != null && !jsonObj.get("title").isJsonNull()) && !jsonObj.get("title").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `title` to be a primitive type in the JSON string but got `%s`", jsonObj.get("title").toString()));
      }
      if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull()) && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if ((jsonObj.get("created") != null && !jsonObj.get("created").isJsonNull()) && !jsonObj.get("created").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `created` to be a primitive type in the JSON string but got `%s`", jsonObj.get("created").toString()));
      }
      if ((jsonObj.get("updated") != null && !jsonObj.get("updated").isJsonNull()) && !jsonObj.get("updated").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `updated` to be a primitive type in the JSON string but got `%s`", jsonObj.get("updated").toString()));
      }
      if (jsonObj.get("cases") != null && !jsonObj.get("cases").isJsonNull()) {
        JsonArray jsonArraycases = jsonObj.getAsJsonArray("cases");
        if (jsonArraycases != null) {
          // ensure the json data is an array
          if (!jsonObj.get("cases").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `cases` to be an array in the JSON string but got `%s`", jsonObj.get("cases").toString()));
          }

          // validate the optional field `cases` (array)
          for (int i = 0; i < jsonArraycases.size(); i++) {
            PlanDetailedAllOfCases.validateJsonElement(jsonArraycases.get(i));
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!PlanDetailed.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'PlanDetailed' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<PlanDetailed> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(PlanDetailed.class));

       return (TypeAdapter<T>) new TypeAdapter<PlanDetailed>() {
           @Override
           public void write(JsonWriter out, PlanDetailed value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public PlanDetailed read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of PlanDetailed given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of PlanDetailed
  * @throws IOException if the JSON string is invalid with respect to PlanDetailed
  */
  public static PlanDetailed fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, PlanDetailed.class);
  }

 /**
  * Convert an instance of PlanDetailed to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

