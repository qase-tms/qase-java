package io.qase.api.inner;

import com.google.gson.*;
import kong.unirest.GenericType;
import kong.unirest.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class GsonObjectMapper implements ObjectMapper {
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(),
                            FORMATTER))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (dateTime, typeOfSrc, context) -> new JsonPrimitive(FORMATTER.format(dateTime)))
            .setPrettyPrinting()
            .create();

    @Override
    public <T> T readValue(String value, Class<T> valueType) {
        return gson.fromJson(value, valueType);
    }

    @Override
    public <T> T readValue(String value, GenericType<T> genericType) {
        return gson.fromJson(value, genericType.getType());
    }

    @Override
    public String writeValue(Object value) {
        return gson.toJson(value);
    }


}
