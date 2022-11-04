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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * SharedStepCreateStepsInner
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-11-04T01:02:11.281898+03:00[Europe/Moscow]")
public class SharedStepCreateStepsInner {
  public static final String SERIALIZED_NAME_HASH = "hash";
  @SerializedName(SERIALIZED_NAME_HASH)
  private String hash;

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
  private List<String> attachments = null;

  public SharedStepCreateStepsInner() {
  }

  public SharedStepCreateStepsInner hash(String hash) {
    
    this.hash = hash;
    return this;
  }

   /**
   * Get hash
   * @return hash
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getHash() {
    return hash;
  }


  public void setHash(String hash) {
    this.hash = hash;
  }


  public SharedStepCreateStepsInner action(String action) {
    
    this.action = action;
    return this;
  }

   /**
   * Get action
   * @return action
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getAction() {
    return action;
  }


  public void setAction(String action) {
    this.action = action;
  }


  public SharedStepCreateStepsInner expectedResult(String expectedResult) {
    
    this.expectedResult = expectedResult;
    return this;
  }

   /**
   * Get expectedResult
   * @return expectedResult
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getExpectedResult() {
    return expectedResult;
  }


  public void setExpectedResult(String expectedResult) {
    this.expectedResult = expectedResult;
  }


  public SharedStepCreateStepsInner data(String data) {
    
    this.data = data;
    return this;
  }

   /**
   * Get data
   * @return data
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getData() {
    return data;
  }


  public void setData(String data) {
    this.data = data;
  }


  public SharedStepCreateStepsInner attachments(List<String> attachments) {
    
    this.attachments = attachments;
    return this;
  }

  public SharedStepCreateStepsInner addAttachmentsItem(String attachmentsItem) {
    if (this.attachments == null) {
      this.attachments = new ArrayList<>();
    }
    this.attachments.add(attachmentsItem);
    return this;
  }

   /**
   * A list of Attachment hashes.
   * @return attachments
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "A list of Attachment hashes.")

  public List<String> getAttachments() {
    return attachments;
  }


  public void setAttachments(List<String> attachments) {
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
    SharedStepCreateStepsInner sharedStepCreateStepsInner = (SharedStepCreateStepsInner) o;
    return Objects.equals(this.hash, sharedStepCreateStepsInner.hash) &&
        Objects.equals(this.action, sharedStepCreateStepsInner.action) &&
        Objects.equals(this.expectedResult, sharedStepCreateStepsInner.expectedResult) &&
        Objects.equals(this.data, sharedStepCreateStepsInner.data) &&
        Objects.equals(this.attachments, sharedStepCreateStepsInner.attachments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hash, action, expectedResult, data, attachments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SharedStepCreateStepsInner {\n");
    sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    expectedResult: ").append(toIndentedString(expectedResult)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
    openapiFields.add("action");
    openapiFields.add("expected_result");
    openapiFields.add("data");
    openapiFields.add("attachments");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("action");
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!SharedStepCreateStepsInner.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'SharedStepCreateStepsInner' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<SharedStepCreateStepsInner> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(SharedStepCreateStepsInner.class));

       return (TypeAdapter<T>) new TypeAdapter<SharedStepCreateStepsInner>() {
           @Override
           public void write(JsonWriter out, SharedStepCreateStepsInner value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public SharedStepCreateStepsInner read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of SharedStepCreateStepsInner given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of SharedStepCreateStepsInner
  * @throws IOException if the JSON string is invalid with respect to SharedStepCreateStepsInner
  */
  public static SharedStepCreateStepsInner fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, SharedStepCreateStepsInner.class);
  }

 /**
  * Convert an instance of SharedStepCreateStepsInner to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

