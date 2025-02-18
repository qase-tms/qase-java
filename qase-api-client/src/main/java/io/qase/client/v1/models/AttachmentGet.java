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
import java.io.IOException;
import java.net.URI;
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
 * AttachmentGet
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-10-07T13:46:56.402996677Z[Etc/UTC]", comments = "Generator version: 7.4.0")
public class AttachmentGet {
  public static final String SERIALIZED_NAME_HASH = "hash";
  @SerializedName(SERIALIZED_NAME_HASH)
  private String hash;

  public static final String SERIALIZED_NAME_FILE = "file";
  @SerializedName(SERIALIZED_NAME_FILE)
  private String _file;

  public static final String SERIALIZED_NAME_MIME = "mime";
  @SerializedName(SERIALIZED_NAME_MIME)
  private String mime;

  public static final String SERIALIZED_NAME_SIZE = "size";
  @SerializedName(SERIALIZED_NAME_SIZE)
  private Integer size;

  public static final String SERIALIZED_NAME_EXTENSION = "extension";
  @SerializedName(SERIALIZED_NAME_EXTENSION)
  private String extension;

  public static final String SERIALIZED_NAME_FULL_PATH = "full_path";
  @SerializedName(SERIALIZED_NAME_FULL_PATH)
  private URI fullPath;

  public static final String SERIALIZED_NAME_URL = "url";
  @SerializedName(SERIALIZED_NAME_URL)
  private String url;

  public AttachmentGet() {
  }

  public AttachmentGet hash(String hash) {
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


  public AttachmentGet _file(String _file) {
    this._file = _file;
    return this;
  }

   /**
   * Get _file
   * @return _file
  **/
  @javax.annotation.Nullable
  public String getFile() {
    return _file;
  }

  public void setFile(String _file) {
    this._file = _file;
  }


  public AttachmentGet mime(String mime) {
    this.mime = mime;
    return this;
  }

   /**
   * Get mime
   * @return mime
  **/
  @javax.annotation.Nullable
  public String getMime() {
    return mime;
  }

  public void setMime(String mime) {
    this.mime = mime;
  }


  public AttachmentGet size(Integer size) {
    this.size = size;
    return this;
  }

   /**
   * Get size
   * @return size
  **/
  @javax.annotation.Nullable
  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }


  public AttachmentGet extension(String extension) {
    this.extension = extension;
    return this;
  }

   /**
   * Get extension
   * @return extension
  **/
  @javax.annotation.Nullable
  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }


  public AttachmentGet fullPath(URI fullPath) {
    this.fullPath = fullPath;
    return this;
  }

   /**
   * Get fullPath
   * @return fullPath
  **/
  @javax.annotation.Nullable
  public URI getFullPath() {
    return fullPath;
  }

  public void setFullPath(URI fullPath) {
    this.fullPath = fullPath;
  }


  public AttachmentGet url(String url) {
    this.url = url;
    return this;
  }

   /**
   * Get url
   * @return url
  **/
  @javax.annotation.Nullable
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AttachmentGet attachmentGet = (AttachmentGet) o;
    return Objects.equals(this.hash, attachmentGet.hash) &&
        Objects.equals(this._file, attachmentGet._file) &&
        Objects.equals(this.mime, attachmentGet.mime) &&
        Objects.equals(this.size, attachmentGet.size) &&
        Objects.equals(this.extension, attachmentGet.extension) &&
        Objects.equals(this.fullPath, attachmentGet.fullPath) &&
        Objects.equals(this.url, attachmentGet.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hash, _file, mime, size, extension, fullPath, url);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AttachmentGet {\n");
    sb.append("    hash: ").append(toIndentedString(hash)).append("\n");
    sb.append("    _file: ").append(toIndentedString(_file)).append("\n");
    sb.append("    mime: ").append(toIndentedString(mime)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    extension: ").append(toIndentedString(extension)).append("\n");
    sb.append("    fullPath: ").append(toIndentedString(fullPath)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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
    openapiFields.add("file");
    openapiFields.add("mime");
    openapiFields.add("size");
    openapiFields.add("extension");
    openapiFields.add("full_path");
    openapiFields.add("url");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to AttachmentGet
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AttachmentGet.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AttachmentGet is not found in the empty JSON string", AttachmentGet.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!AttachmentGet.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AttachmentGet` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("hash") != null && !jsonObj.get("hash").isJsonNull()) && !jsonObj.get("hash").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `hash` to be a primitive type in the JSON string but got `%s`", jsonObj.get("hash").toString()));
      }
      if ((jsonObj.get("file") != null && !jsonObj.get("file").isJsonNull()) && !jsonObj.get("file").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `file` to be a primitive type in the JSON string but got `%s`", jsonObj.get("file").toString()));
      }
      if ((jsonObj.get("mime") != null && !jsonObj.get("mime").isJsonNull()) && !jsonObj.get("mime").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `mime` to be a primitive type in the JSON string but got `%s`", jsonObj.get("mime").toString()));
      }
      if ((jsonObj.get("extension") != null && !jsonObj.get("extension").isJsonNull()) && !jsonObj.get("extension").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `extension` to be a primitive type in the JSON string but got `%s`", jsonObj.get("extension").toString()));
      }
      if ((jsonObj.get("full_path") != null && !jsonObj.get("full_path").isJsonNull()) && !jsonObj.get("full_path").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `full_path` to be a primitive type in the JSON string but got `%s`", jsonObj.get("full_path").toString()));
      }
      if ((jsonObj.get("url") != null && !jsonObj.get("url").isJsonNull()) && !jsonObj.get("url").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `url` to be a primitive type in the JSON string but got `%s`", jsonObj.get("url").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AttachmentGet.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AttachmentGet' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AttachmentGet> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AttachmentGet.class));

       return (TypeAdapter<T>) new TypeAdapter<AttachmentGet>() {
           @Override
           public void write(JsonWriter out, AttachmentGet value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AttachmentGet read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of AttachmentGet given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AttachmentGet
  * @throws IOException if the JSON string is invalid with respect to AttachmentGet
  */
  public static AttachmentGet fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AttachmentGet.class);
  }

 /**
  * Convert an instance of AttachmentGet to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

