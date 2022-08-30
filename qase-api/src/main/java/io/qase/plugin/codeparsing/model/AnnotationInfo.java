package io.qase.plugin.codeparsing.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ToString
public class AnnotationInfo {

    @Setter
    @Getter
    private String annotationClassCanonicalName;

    private final Map<String, Object> attributes = new HashMap<>();

    public void addAttribute(String name, Object value) {
        Object previousValue = attributes.put(name, value);
        if (previousValue != null) {
            log.warn(
                "{}: the value of {} has been changed from {} to {}",
                annotationClassCanonicalName, name, previousValue, value
            );
        }
    }

    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }
}
