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
import io.qase.client.v1.models.DefectQuery;
import io.qase.client.v1.models.PlanQuery;
import io.qase.client.v1.models.RequirementQuery;
import io.qase.client.v1.models.ResultQuery;
import io.qase.client.v1.models.RunEnvironment;
import io.qase.client.v1.models.RunMilestone;
import io.qase.client.v1.models.RunQuery;
import io.qase.client.v1.models.RunStats;
import io.qase.client.v1.models.TagValue;
import io.qase.client.v1.models.TestCaseParams;
import io.qase.client.v1.models.TestCaseQuery;
import io.qase.client.v1.models.TestStep;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;



import java.io.IOException;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;

import io.qase.client.v1.JSON;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.4.0")
public class SearchResponseAllOfResultEntities extends AbstractOpenApiSchema {
    private static final Logger log = Logger.getLogger(SearchResponseAllOfResultEntities.class.getName());

    public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (!SearchResponseAllOfResultEntities.class.isAssignableFrom(type.getRawType())) {
                return null; // this class only serializes 'SearchResponseAllOfResultEntities' and its subtypes
            }
            final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
            final TypeAdapter<RunQuery> adapterRunQuery = gson.getDelegateAdapter(this, TypeToken.get(RunQuery.class));
            final TypeAdapter<ResultQuery> adapterResultQuery = gson.getDelegateAdapter(this, TypeToken.get(ResultQuery.class));
            final TypeAdapter<RequirementQuery> adapterRequirementQuery = gson.getDelegateAdapter(this, TypeToken.get(RequirementQuery.class));
            final TypeAdapter<TestCaseQuery> adapterTestCaseQuery = gson.getDelegateAdapter(this, TypeToken.get(TestCaseQuery.class));
            final TypeAdapter<DefectQuery> adapterDefectQuery = gson.getDelegateAdapter(this, TypeToken.get(DefectQuery.class));
            final TypeAdapter<PlanQuery> adapterPlanQuery = gson.getDelegateAdapter(this, TypeToken.get(PlanQuery.class));

            return (TypeAdapter<T>) new TypeAdapter<SearchResponseAllOfResultEntities>() {
                @Override
                public void write(JsonWriter out, SearchResponseAllOfResultEntities value) throws IOException {
                    if (value == null || value.getActualInstance() == null) {
                        elementAdapter.write(out, null);
                        return;
                    }

                    // check if the actual instance is of the type `RunQuery`
                    if (value.getActualInstance() instanceof RunQuery) {
                      JsonElement element = adapterRunQuery.toJsonTree((RunQuery)value.getActualInstance());
                      elementAdapter.write(out, element);
                      return;
                    }
                    // check if the actual instance is of the type `ResultQuery`
                    if (value.getActualInstance() instanceof ResultQuery) {
                      JsonElement element = adapterResultQuery.toJsonTree((ResultQuery)value.getActualInstance());
                      elementAdapter.write(out, element);
                      return;
                    }
                    // check if the actual instance is of the type `RequirementQuery`
                    if (value.getActualInstance() instanceof RequirementQuery) {
                      JsonElement element = adapterRequirementQuery.toJsonTree((RequirementQuery)value.getActualInstance());
                      elementAdapter.write(out, element);
                      return;
                    }
                    // check if the actual instance is of the type `TestCaseQuery`
                    if (value.getActualInstance() instanceof TestCaseQuery) {
                      JsonElement element = adapterTestCaseQuery.toJsonTree((TestCaseQuery)value.getActualInstance());
                      elementAdapter.write(out, element);
                      return;
                    }
                    // check if the actual instance is of the type `DefectQuery`
                    if (value.getActualInstance() instanceof DefectQuery) {
                      JsonElement element = adapterDefectQuery.toJsonTree((DefectQuery)value.getActualInstance());
                      elementAdapter.write(out, element);
                      return;
                    }
                    // check if the actual instance is of the type `PlanQuery`
                    if (value.getActualInstance() instanceof PlanQuery) {
                      JsonElement element = adapterPlanQuery.toJsonTree((PlanQuery)value.getActualInstance());
                      elementAdapter.write(out, element);
                      return;
                    }
                    throw new IOException("Failed to serialize as the type doesn't match oneOf schemas: DefectQuery, PlanQuery, RequirementQuery, ResultQuery, RunQuery, TestCaseQuery");
                }

                @Override
                public SearchResponseAllOfResultEntities read(JsonReader in) throws IOException {
                    Object deserialized = null;
                    JsonElement jsonElement = elementAdapter.read(in);

                    int match = 0;
                    ArrayList<String> errorMessages = new ArrayList<>();
                    TypeAdapter actualAdapter = elementAdapter;

                    // deserialize RunQuery
                    try {
                      // validate the JSON object to see if any exception is thrown
                      RunQuery.validateJsonElement(jsonElement);
                      actualAdapter = adapterRunQuery;
                      match++;
                      log.log(Level.FINER, "Input data matches schema 'RunQuery'");
                    } catch (Exception e) {
                      // deserialization failed, continue
                      errorMessages.add(String.format("Deserialization for RunQuery failed with `%s`.", e.getMessage()));
                      log.log(Level.FINER, "Input data does not match schema 'RunQuery'", e);
                    }
                    // deserialize ResultQuery
                    try {
                      // validate the JSON object to see if any exception is thrown
                      ResultQuery.validateJsonElement(jsonElement);
                      actualAdapter = adapterResultQuery;
                      match++;
                      log.log(Level.FINER, "Input data matches schema 'ResultQuery'");
                    } catch (Exception e) {
                      // deserialization failed, continue
                      errorMessages.add(String.format("Deserialization for ResultQuery failed with `%s`.", e.getMessage()));
                      log.log(Level.FINER, "Input data does not match schema 'ResultQuery'", e);
                    }
                    // deserialize RequirementQuery
                    try {
                      // validate the JSON object to see if any exception is thrown
                      RequirementQuery.validateJsonElement(jsonElement);
                      actualAdapter = adapterRequirementQuery;
                      match++;
                      log.log(Level.FINER, "Input data matches schema 'RequirementQuery'");
                    } catch (Exception e) {
                      // deserialization failed, continue
                      errorMessages.add(String.format("Deserialization for RequirementQuery failed with `%s`.", e.getMessage()));
                      log.log(Level.FINER, "Input data does not match schema 'RequirementQuery'", e);
                    }
                    // deserialize TestCaseQuery
                    try {
                      // validate the JSON object to see if any exception is thrown
                      TestCaseQuery.validateJsonElement(jsonElement);
                      actualAdapter = adapterTestCaseQuery;
                      match++;
                      log.log(Level.FINER, "Input data matches schema 'TestCaseQuery'");
                    } catch (Exception e) {
                      // deserialization failed, continue
                      errorMessages.add(String.format("Deserialization for TestCaseQuery failed with `%s`.", e.getMessage()));
                      log.log(Level.FINER, "Input data does not match schema 'TestCaseQuery'", e);
                    }
                    // deserialize DefectQuery
                    try {
                      // validate the JSON object to see if any exception is thrown
                      DefectQuery.validateJsonElement(jsonElement);
                      actualAdapter = adapterDefectQuery;
                      match++;
                      log.log(Level.FINER, "Input data matches schema 'DefectQuery'");
                    } catch (Exception e) {
                      // deserialization failed, continue
                      errorMessages.add(String.format("Deserialization for DefectQuery failed with `%s`.", e.getMessage()));
                      log.log(Level.FINER, "Input data does not match schema 'DefectQuery'", e);
                    }
                    // deserialize PlanQuery
                    try {
                      // validate the JSON object to see if any exception is thrown
                      PlanQuery.validateJsonElement(jsonElement);
                      actualAdapter = adapterPlanQuery;
                      match++;
                      log.log(Level.FINER, "Input data matches schema 'PlanQuery'");
                    } catch (Exception e) {
                      // deserialization failed, continue
                      errorMessages.add(String.format("Deserialization for PlanQuery failed with `%s`.", e.getMessage()));
                      log.log(Level.FINER, "Input data does not match schema 'PlanQuery'", e);
                    }

                    if (match == 1) {
                        SearchResponseAllOfResultEntities ret = new SearchResponseAllOfResultEntities();
                        ret.setActualInstance(actualAdapter.fromJsonTree(jsonElement));
                        return ret;
                    }

                    throw new IOException(String.format("Failed deserialization for SearchResponseAllOfResultEntities: %d classes match result, expected 1. Detailed failure message for oneOf schemas: %s. JSON: %s", match, errorMessages, jsonElement.toString()));
                }
            }.nullSafe();
        }
    }

    // store a list of schema names defined in oneOf
    public static final Map<String, Class<?>> schemas = new HashMap<String, Class<?>>();

    public SearchResponseAllOfResultEntities() {
        super("oneOf", Boolean.FALSE);
    }

    public SearchResponseAllOfResultEntities(DefectQuery o) {
        super("oneOf", Boolean.FALSE);
        setActualInstance(o);
    }

    public SearchResponseAllOfResultEntities(PlanQuery o) {
        super("oneOf", Boolean.FALSE);
        setActualInstance(o);
    }

    public SearchResponseAllOfResultEntities(RequirementQuery o) {
        super("oneOf", Boolean.FALSE);
        setActualInstance(o);
    }

    public SearchResponseAllOfResultEntities(ResultQuery o) {
        super("oneOf", Boolean.FALSE);
        setActualInstance(o);
    }

    public SearchResponseAllOfResultEntities(RunQuery o) {
        super("oneOf", Boolean.FALSE);
        setActualInstance(o);
    }

    public SearchResponseAllOfResultEntities(TestCaseQuery o) {
        super("oneOf", Boolean.FALSE);
        setActualInstance(o);
    }

    static {
        schemas.put("RunQuery", RunQuery.class);
        schemas.put("ResultQuery", ResultQuery.class);
        schemas.put("RequirementQuery", RequirementQuery.class);
        schemas.put("TestCaseQuery", TestCaseQuery.class);
        schemas.put("DefectQuery", DefectQuery.class);
        schemas.put("PlanQuery", PlanQuery.class);
    }

    @Override
    public Map<String, Class<?>> getSchemas() {
        return SearchResponseAllOfResultEntities.schemas;
    }

    /**
     * Set the instance that matches the oneOf child schema, check
     * the instance parameter is valid against the oneOf child schemas:
     * DefectQuery, PlanQuery, RequirementQuery, ResultQuery, RunQuery, TestCaseQuery
     *
     * It could be an instance of the 'oneOf' schemas.
     */
    @Override
    public void setActualInstance(Object instance) {
        if (instance instanceof RunQuery) {
            super.setActualInstance(instance);
            return;
        }

        if (instance instanceof ResultQuery) {
            super.setActualInstance(instance);
            return;
        }

        if (instance instanceof RequirementQuery) {
            super.setActualInstance(instance);
            return;
        }

        if (instance instanceof TestCaseQuery) {
            super.setActualInstance(instance);
            return;
        }

        if (instance instanceof DefectQuery) {
            super.setActualInstance(instance);
            return;
        }

        if (instance instanceof PlanQuery) {
            super.setActualInstance(instance);
            return;
        }

        throw new RuntimeException("Invalid instance type. Must be DefectQuery, PlanQuery, RequirementQuery, ResultQuery, RunQuery, TestCaseQuery");
    }

    /**
     * Get the actual instance, which can be the following:
     * DefectQuery, PlanQuery, RequirementQuery, ResultQuery, RunQuery, TestCaseQuery
     *
     * @return The actual instance (DefectQuery, PlanQuery, RequirementQuery, ResultQuery, RunQuery, TestCaseQuery)
     */
    @Override
    public Object getActualInstance() {
        return super.getActualInstance();
    }

    /**
     * Get the actual instance of `RunQuery`. If the actual instance is not `RunQuery`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `RunQuery`
     * @throws ClassCastException if the instance is not `RunQuery`
     */
    public RunQuery getRunQuery() throws ClassCastException {
        return (RunQuery)super.getActualInstance();
    }
    /**
     * Get the actual instance of `ResultQuery`. If the actual instance is not `ResultQuery`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `ResultQuery`
     * @throws ClassCastException if the instance is not `ResultQuery`
     */
    public ResultQuery getResultQuery() throws ClassCastException {
        return (ResultQuery)super.getActualInstance();
    }
    /**
     * Get the actual instance of `RequirementQuery`. If the actual instance is not `RequirementQuery`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `RequirementQuery`
     * @throws ClassCastException if the instance is not `RequirementQuery`
     */
    public RequirementQuery getRequirementQuery() throws ClassCastException {
        return (RequirementQuery)super.getActualInstance();
    }
    /**
     * Get the actual instance of `TestCaseQuery`. If the actual instance is not `TestCaseQuery`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `TestCaseQuery`
     * @throws ClassCastException if the instance is not `TestCaseQuery`
     */
    public TestCaseQuery getTestCaseQuery() throws ClassCastException {
        return (TestCaseQuery)super.getActualInstance();
    }
    /**
     * Get the actual instance of `DefectQuery`. If the actual instance is not `DefectQuery`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `DefectQuery`
     * @throws ClassCastException if the instance is not `DefectQuery`
     */
    public DefectQuery getDefectQuery() throws ClassCastException {
        return (DefectQuery)super.getActualInstance();
    }
    /**
     * Get the actual instance of `PlanQuery`. If the actual instance is not `PlanQuery`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `PlanQuery`
     * @throws ClassCastException if the instance is not `PlanQuery`
     */
    public PlanQuery getPlanQuery() throws ClassCastException {
        return (PlanQuery)super.getActualInstance();
    }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to SearchResponseAllOfResultEntities
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
    // validate oneOf schemas one by one
    int validCount = 0;
    ArrayList<String> errorMessages = new ArrayList<>();
    // validate the json string with RunQuery
    try {
      RunQuery.validateJsonElement(jsonElement);
      validCount++;
    } catch (Exception e) {
      errorMessages.add(String.format("Deserialization for RunQuery failed with `%s`.", e.getMessage()));
      // continue to the next one
    }
    // validate the json string with ResultQuery
    try {
      ResultQuery.validateJsonElement(jsonElement);
      validCount++;
    } catch (Exception e) {
      errorMessages.add(String.format("Deserialization for ResultQuery failed with `%s`.", e.getMessage()));
      // continue to the next one
    }
    // validate the json string with RequirementQuery
    try {
      RequirementQuery.validateJsonElement(jsonElement);
      validCount++;
    } catch (Exception e) {
      errorMessages.add(String.format("Deserialization for RequirementQuery failed with `%s`.", e.getMessage()));
      // continue to the next one
    }
    // validate the json string with TestCaseQuery
    try {
      TestCaseQuery.validateJsonElement(jsonElement);
      validCount++;
    } catch (Exception e) {
      errorMessages.add(String.format("Deserialization for TestCaseQuery failed with `%s`.", e.getMessage()));
      // continue to the next one
    }
    // validate the json string with DefectQuery
    try {
      DefectQuery.validateJsonElement(jsonElement);
      validCount++;
    } catch (Exception e) {
      errorMessages.add(String.format("Deserialization for DefectQuery failed with `%s`.", e.getMessage()));
      // continue to the next one
    }
    // validate the json string with PlanQuery
    try {
      PlanQuery.validateJsonElement(jsonElement);
      validCount++;
    } catch (Exception e) {
      errorMessages.add(String.format("Deserialization for PlanQuery failed with `%s`.", e.getMessage()));
      // continue to the next one
    }
    if (validCount != 1) {
      throw new IOException(String.format("The JSON string is invalid for SearchResponseAllOfResultEntities with oneOf schemas: DefectQuery, PlanQuery, RequirementQuery, ResultQuery, RunQuery, TestCaseQuery. %d class(es) match the result, expected 1. Detailed failure message for oneOf schemas: %s. JSON: %s", validCount, errorMessages, jsonElement.toString()));
    }
  }

 /**
  * Create an instance of SearchResponseAllOfResultEntities given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of SearchResponseAllOfResultEntities
  * @throws IOException if the JSON string is invalid with respect to SearchResponseAllOfResultEntities
  */
  public static SearchResponseAllOfResultEntities fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, SearchResponseAllOfResultEntities.class);
  }

 /**
  * Convert an instance of SearchResponseAllOfResultEntities to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

