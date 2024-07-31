package io.qase.api.inner;

import com.google.gson.annotations.SerializedName;
import io.qase.client.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FilterHelper {
    private FilterHelper() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static List<Pair> getFilterPairs(Object filterModel) {
        List<Pair> pairs = new ArrayList<>();
        Class<?> aClass = filterModel.getClass();
        List<Field> fields = Arrays.stream(aClass.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toList());
        for (Field field : fields) {
            Object value = null;
            try {
                value = field.get(filterModel);
            } catch (IllegalAccessException e) {
                // ignore
            }
            if (value != null) {
                String name;
                SerializedName serializedName = field.getAnnotation(SerializedName.class);
                if (serializedName != null) {
                    name = serializedName.value();
                } else {
                    name = field.getName();
                }
                pairs.add(new Pair("filters[" + name + "]", value.toString()));
            }
        }
        return pairs;
    }
}
