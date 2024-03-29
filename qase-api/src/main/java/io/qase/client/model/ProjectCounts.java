/*
 * Qase.io API
 * Qase API Specification.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: support@qase.io
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.qase.client.model;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.qase.client.JSON;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

/**
 * ProjectCounts
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-11-04T01:02:11.281898+03:00[Europe/Moscow]")
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
  @ApiModelProperty(value = "")

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
  @ApiModelProperty(value = "")

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
  @ApiModelProperty(value = "")

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
  @ApiModelProperty(value = "")

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
  @ApiModelProperty(value = "")

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
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             return thisAdapter.fromJsonTree(jsonObj);
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

