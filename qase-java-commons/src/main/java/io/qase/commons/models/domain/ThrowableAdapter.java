package io.qase.commons.models.domain;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

class ThrowableAdapter extends TypeAdapter<Throwable> {
    @Override
    public void write(JsonWriter out, Throwable value) throws IOException {
        out.beginObject();
        if (value != null) {
            out.name("message").value(value.getMessage());
            out.name("class").value(value.getClass().getName());
        } else {
            out.name("message").value("null");
            out.name("class").value("Unknown");
        }
        out.endObject();
    }

    @Override
    public Throwable read(JsonReader in) {
        return null; // Десериализация не поддерживается
    }
}
