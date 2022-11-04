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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * PlanDetailedAllOf
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-11-04T01:02:11.281898+03:00[Europe/Moscow]")
public class PlanDetailedAllOf {
  public static final String SERIALIZED_NAME_AVERAGE_TIME = "average_time";
  @SerializedName(SERIALIZED_NAME_AVERAGE_TIME)
  private BigDecimal averageTime;

  public static final String SERIALIZED_NAME_CASES = "cases";
  @SerializedName(SERIALIZED_NAME_CASES)
  private List<PlanDetailedAllOfCases> cases = null;

  public PlanDetailedAllOf() {
  }

  public PlanDetailedAllOf averageTime(BigDecimal averageTime) {
    
    this.averageTime = averageTime;
    return this;
  }

   /**
   * Get averageTime
   * @return averageTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public BigDecimal getAverageTime() {
    return averageTime;
  }


  public void setAverageTime(BigDecimal averageTime) {
    this.averageTime = averageTime;
  }


  public PlanDetailedAllOf cases(List<PlanDetailedAllOfCases> cases) {
    
    this.cases = cases;
    return this;
  }

  public PlanDetailedAllOf addCasesItem(PlanDetailedAllOfCases casesItem) {
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
  @ApiModelProperty(value = "")

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
    PlanDetailedAllOf planDetailedAllOf = (PlanDetailedAllOf) o;
    return Objects.equals(this.averageTime, planDetailedAllOf.averageTime) &&
        Objects.equals(this.cases, planDetailedAllOf.cases);
  }

  @Override
  public int hashCode() {
    return Objects.hash(averageTime, cases);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlanDetailedAllOf {\n");
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
    openapiFields.add("average_time");
    openapiFields.add("cases");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!PlanDetailedAllOf.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'PlanDetailedAllOf' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<PlanDetailedAllOf> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(PlanDetailedAllOf.class));

       return (TypeAdapter<T>) new TypeAdapter<PlanDetailedAllOf>() {
           @Override
           public void write(JsonWriter out, PlanDetailedAllOf value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public PlanDetailedAllOf read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of PlanDetailedAllOf given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of PlanDetailedAllOf
  * @throws IOException if the JSON string is invalid with respect to PlanDetailedAllOf
  */
  public static PlanDetailedAllOf fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, PlanDetailedAllOf.class);
  }

 /**
  * Convert an instance of PlanDetailedAllOf to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

