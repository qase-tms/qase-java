package io.qase.api.services;

import io.qase.api.enums.DefectStatus;
import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.defects.Defect;
import io.qase.api.models.v1.defects.Defects;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public interface DefectService {

    Defects getAll(String projectCode, int limit, int offset, RouteFilter filter);

    Defects getAll(String projectCode);

    Defect get(String projectCode, long id);

    boolean resolve(String projectCode, long defectId);

    boolean delete(String projectCode, long defectId);

    default Filter filter() {
        return new Filter();
    }


    class Filter implements RouteFilter {
        private final Map<Filters, String> filters = new EnumMap<>(Filters.class);

        private Filter() {
        }

        @Override
        public Map<Filters, String> getFilters() {
            return Collections.unmodifiableMap(filters);
        }

        /**
         * @param status A list of status values. Possible values: active, complete, abort
         * @return
         */
        public Filter status(DefectStatus status) {
            filters.put(Filters.status, status.name());
            return this;
        }
    }
}
