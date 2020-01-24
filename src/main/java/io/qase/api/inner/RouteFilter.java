package io.qase.api.inner;

import io.qase.api.enums.Filters;

import java.util.Map;

public interface RouteFilter {
    Map<Filters, String> getFilters();
}
