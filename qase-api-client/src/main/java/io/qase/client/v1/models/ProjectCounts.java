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
import io.qase.client.v1.models.ProjectCountsDefects;
import io.qase.client.v1.models.ProjectCountsRuns;
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

import io.qase.client.v1.JSON;

/**
 * ProjectCounts
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-10-07T13:46:56.402996677Z[Etc/UTC]", comments = "Generator version: 7.4.0")
public class ProjectCounts {
  public static final String SERIALIZED_NAME_CASES = "cases";
  @SerializedName(SERIALIZED_NAME_CASES)
  private Integer cases;

  public static final String SERIALIZED_NAME_SUITES = "suites";
  @SerializedName(SERIALIZED_NAME_SUITES)
  private Integer suites;

  public static final String SERIALIZED_NAME_MILESTONES = "milestones";
  @SerializedName(SERIALIZED_NAME_MILESTONES)
  private Integer milestones;

  public static final String SERIALIZED_NAME_RUNS = "runs";
  @SerializedName(SERIALIZED_NAME_RUNS)
  private ProjectCountsRuns runs;

  public static final String SERIALIZED_NAME_DEFECTS = "defects";
  @SerializedName(SERIALIZED_NAME_DEFECTS)
  private ProjectCountsDefects defects;

  public ProjectCounts() {
  }

  public ProjectCounts cases(Integer cases) {
    this.cases = cases;
    return this;
  }

   /**
   * Get cases
   * @return cases
  **/
  @javax.annotation.Nullable
  public Integer getCases() {
    return cases;
  }

  public void setCases(Integer cases) {
    this.cases = cases;
  }


  public ProjectCounts suites(Integer suites) {
    this.suites = suites;
    return this;
  }

   /**
   * Get suites
   * @return suites
  **/
  @javax.annotation.Nullable
  public Integer getSuites() {
    return suites;
  }

  public void setSuites(Integer suites) {
    this.suites = suites;
  }


  public ProjectCounts milestones(Integer milestones) {
    this.milestones = milestones;
    return this;
  }

   /**
   * Get milestones
   * @return milestones
  **/
  @javax.annotation.Nullable
  public Integer getMilestones() {
    return milestones;
  }

  public void setMilestones(Integer milestones) {
    this.milestones = milestones;
  }


  public ProjectCounts runs(ProjectCountsRuns runs) {
    this.runs = runs;
    return this;
  }

   /**
   * Get runs
   * @return runs
  **/
  @javax.annotation.Nullable
  public ProjectCountsRuns getRuns() {
    return runs;
  }

  public void setRuns(ProjectCountsRuns runs) {
    this.runs = runs;
  }


  public ProjectCounts defects(ProjectCountsDefects defects) {
    this.defects = defects;
    return this;
  }

   /**
   * Get defects
   * @return defects
  **/
  @javax.annotation.Nullable
  public ProjectCountsDefects getDefects() {
    return defects;
  }

  public void setDefects(ProjectCountsDefects defects) {
    this.defects = defects;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectCounts projectCounts = (ProjectCounts) o;
    return Objects.equals(this.cases, projectCounts.cases) &&
        Objects.equals(this.suites, projectCounts.suites) &&
        Objects.equals(this.milestones, projectCounts.milestones) &&
        Objects.equals(this.runs, projectCounts.runs) &&
        Objects.equals(this.defects, projectCounts.defects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cases, suites, milestones, runs, defects);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectCounts {\n");
    sb.append("    cases: ").append(toIndentedString(cases)).append("\n");
    sb.append("    suites: ").append(toIndentedString(suites)).append("\n");
    sb.append("    milestones: ").append(toIndentedString(milestones)).append("\n");
    sb.append("    runs: ").append(toIndentedString(runs)).append("\n");
    sb.append("    defects: ").append(toIndentedString(defects)).append("\n");
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
    openapiFields.add("cases");
    openapiFields.add("suites");
    openapiFields.add("milestones");
    openapiFields.add("runs");
    openapiFields.add("defects");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ProjectCounts
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ProjectCounts.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ProjectCounts is not found in the empty JSON string", ProjectCounts.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!ProjectCounts.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ProjectCounts` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // validate the optional field `runs`
      if (jsonObj.get("runs") != null && !jsonObj.get("runs").isJsonNull()) {
        ProjectCountsRuns.validateJsonElement(jsonObj.get("runs"));
      }
      // validate the optional field `defects`
      if (jsonObj.get("defects") != null && !jsonObj.get("defects").isJsonNull()) {
        ProjectCountsDefects.validateJsonElement(jsonObj.get("defects"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ProjectCounts.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ProjectCounts' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ProjectCounts> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ProjectCounts.class));

       return (TypeAdapter<T>) new TypeAdapter<ProjectCounts>() {
           @Override
           public void write(JsonWriter out, ProjectCounts value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ProjectCounts read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ProjectCounts given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ProjectCounts
  * @throws IOException if the JSON string is invalid with respect to ProjectCounts
  */
  public static ProjectCounts fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ProjectCounts.class);
  }

 /**
  * Convert an instance of ProjectCounts to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

