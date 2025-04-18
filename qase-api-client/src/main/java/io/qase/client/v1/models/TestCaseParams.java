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
public class TestCaseParams extends AbstractOpenApiSchema {
    private static final Logger log = Logger.getLogger(TestCaseParams.class.getName());

    public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (!TestCaseParams.class.isAssignableFrom(type.getRawType())) {
                return null; // this class only serializes 'TestCaseParams' and its subtypes
            }
            final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

            final Type typeInstance = new TypeToken<List<Object>>() {
            }.getType();
            final TypeAdapter<List<Object>> adapterList = (TypeAdapter<List<Object>>) gson.getDelegateAdapter(this, TypeToken.get(typeInstance));
            final TypeAdapter<Object> adapterObject = gson.getDelegateAdapter(this, TypeToken.get(Object.class));

            return (TypeAdapter<T>) new TypeAdapter<TestCaseParams>() {
                @Override
                public void write(JsonWriter out, TestCaseParams value) throws IOException {
                    if (value == null || value.getActualInstance() == null) {
                        elementAdapter.write(out, null);
                        return;
                    }

                    // check if the actual instance is of the type `List<Object>`
                    if (value.getActualInstance() instanceof List) {
                        JsonPrimitive primitive = adapterList.toJsonTree((List<Object>) value.getActualInstance()).getAsJsonPrimitive();
                        elementAdapter.write(out, primitive);
                        return;
                    }

                    // check if the actual instance is of the type `Object`
                    if (value.getActualInstance() instanceof Object) {
                        JsonPrimitive primitive = adapterObject.toJsonTree((Object) value.getActualInstance()).getAsJsonPrimitive();
                        elementAdapter.write(out, primitive);
                        return;
                    }
                    throw new IOException("Failed to serialize as the type doesn't match anyOf schemae: List<Object>, Object");
                }

                @Override
                public TestCaseParams read(JsonReader in) throws IOException {
                    Object deserialized = null;
                    JsonElement jsonElement = elementAdapter.read(in);

                    ArrayList<String> errorMessages = new ArrayList<>();
                    TypeAdapter actualAdapter = elementAdapter;

                    // deserialize List<Object>
                    try {
                        // validate the JSON object to see if any exception is thrown
                        if (!jsonElement.getAsJsonPrimitive().isNumber()) {
                            throw new IllegalArgumentException(String.format("Expected json element to be of type Number in the JSON string but got `%s`", jsonElement.toString()));
                        }
                        actualAdapter = adapterList;
                        if (!jsonElement.isJsonArray()) {
                            throw new IllegalArgumentException(String.format("Expected json element to be a array type in the JSON string but got `%s`", jsonElement.toString()));
                        }

                        JsonArray array = jsonElement.getAsJsonArray();
                        // validate array items
                        for (JsonElement element : array) {
                            if (!element.getAsJsonPrimitive().isNumber()) {
                                throw new IllegalArgumentException(String.format("Expected array items to be of type Number in the JSON string but got `%s`", jsonElement.toString()));
                            }
                        }
                        actualAdapter = adapterList;
                        TestCaseParams ret = new TestCaseParams();
                        ret.setActualInstance(actualAdapter.fromJsonTree(jsonElement));
                        return ret;
                    } catch (Exception e) {
                        // deserialization failed, continue
                        errorMessages.add(String.format("Deserialization for List<Object> failed with `%s`.", e.getMessage()));
                        log.log(Level.FINER, "Input data does not match schema 'List<Object>'", e);
                    }
                    // deserialize Object
                    try {
                        // validate the JSON object to see if any exception is thrown
                        if (!jsonElement.getAsJsonPrimitive().isNumber()) {
                            throw new IllegalArgumentException(String.format("Expected json element to be of type Number in the JSON string but got `%s`", jsonElement.toString()));
                        }
                        actualAdapter = adapterObject;
                        TestCaseParams ret = new TestCaseParams();
                        ret.setActualInstance(actualAdapter.fromJsonTree(jsonElement));
                        return ret;
                    } catch (Exception e) {
                        // deserialization failed, continue
                        errorMessages.add(String.format("Deserialization for Object failed with `%s`.", e.getMessage()));
                        log.log(Level.FINER, "Input data does not match schema 'Object'", e);
                    }

                    throw new IOException(String.format("Failed deserialization for TestCaseParams: no class matches result, expected at least 1. Detailed failure message for anyOf schemas: %s. JSON: %s", errorMessages, jsonElement.toString()));
                }
            }.nullSafe();
        }
    }

    // store a list of schema names defined in anyOf
    public static final Map<String, Class<?>> schemas = new HashMap<String, Class<?>>();

    public TestCaseParams() {
        super("anyOf", Boolean.FALSE);
    }

    public TestCaseParams(List<Object> o) {
        super("anyOf", Boolean.FALSE);
        setActualInstance(o);
    }

    public TestCaseParams(Object o) {
        super("anyOf", Boolean.FALSE);
        setActualInstance(o);
    }

    static {
        schemas.put("List<Object>", List.class);
        schemas.put("Object", Object.class);
    }

    @Override
    public Map<String, Class<?>> getSchemas() {
        return TestCaseParams.schemas;
    }

    /**
     * Set the instance that matches the anyOf child schema, check
     * the instance parameter is valid against the anyOf child schemas:
     * List<Object>, Object
     * <p>
     * It could be an instance of the 'anyOf' schemas.
     */
    @Override
    public void setActualInstance(Object instance) {
        if (instance instanceof List<?>) {
            List<?> list = (List<?>) instance;
            if (list.get(0) instanceof Object) {
                super.setActualInstance(instance);
                return;
            }
        }

        if (instance instanceof Object) {
            super.setActualInstance(instance);
            return;
        }

        throw new RuntimeException("Invalid instance type. Must be List<Object>, Object");
    }

    /**
     * Get the actual instance, which can be the following:
     * List<Object>, Object
     *
     * @return The actual instance (List<Object>, Object)
     */
    @Override
    public Object getActualInstance() {
        return super.getActualInstance();
    }

    /**
     * Get the actual instance of `List<Object>`. If the actual instance is not `List<Object>`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `List<Object>`
     * @throws ClassCastException if the instance is not `List<Object>`
     */
    public List<Object> getList() throws ClassCastException {
        return (List<Object>) super.getActualInstance();
    }

    /**
     * Get the actual instance of `Object`. If the actual instance is not `Object`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `Object`
     * @throws ClassCastException if the instance is not `Object`
     */
    public Object getObject() throws ClassCastException {
        return (Object) super.getActualInstance();
    }

    /**
     * Validates the JSON Element and throws an exception if issues found
     *
     * @param jsonElement JSON Element
     * @throws IOException if the JSON Element is invalid with respect to TestCaseParams
     */
    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
        // validate anyOf schemas one by one
        ArrayList<String> errorMessages = new ArrayList<>();
        // validate the json string with List<Object>
        try {
            if (!jsonElement.getAsJsonPrimitive().isNumber()) {
                throw new IllegalArgumentException(String.format("Expected json element to be of type Number in the JSON string but got `%s`", jsonElement.toString()));
            }
            if (!jsonElement.isJsonArray()) {
                throw new IllegalArgumentException(String.format("Expected json element to be a array type in the JSON string but got `%s`", jsonElement.toString()));
            }
            JsonArray array = jsonElement.getAsJsonArray();
            // validate array items
            for (JsonElement element : array) {
                if (!element.getAsJsonPrimitive().isNumber()) {
                    throw new IllegalArgumentException(String.format("Expected array items to be of type Number in the JSON string but got `%s`", jsonElement.toString()));
                }
            }
            return;
        } catch (Exception e) {
            errorMessages.add(String.format("Deserialization for List<Object> failed with `%s`.", e.getMessage()));
            // continue to the next one
        }
        // validate the json string with Object
        try {
            if (!jsonElement.getAsJsonPrimitive().isNumber()) {
                throw new IllegalArgumentException(String.format("Expected json element to be of type Number in the JSON string but got `%s`", jsonElement.toString()));
            }
            return;
        } catch (Exception e) {
            errorMessages.add(String.format("Deserialization for Object failed with `%s`.", e.getMessage()));
            // continue to the next one
        }
        throw new IOException(String.format("The JSON string is invalid for TestCaseParams with anyOf schemas: List<Object>, Object. no class match the result, expected at least 1. Detailed failure message for anyOf schemas: %s. JSON: %s", errorMessages, jsonElement.toString()));

    }

    /**
     * Create an instance of TestCaseParams given an JSON string
     *
     * @param jsonString JSON string
     * @return An instance of TestCaseParams
     * @throws IOException if the JSON string is invalid with respect to TestCaseParams
     */
    public static TestCaseParams fromJson(String jsonString) throws IOException {
        return JSON.getGson().fromJson(jsonString, TestCaseParams.class);
    }

    /**
     * Convert an instance of TestCaseParams to an JSON string
     *
     * @return JSON string
     */
    public String toJson() {
        return JSON.getGson().toJson(this);
    }
}

