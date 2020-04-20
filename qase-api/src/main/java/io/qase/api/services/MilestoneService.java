package io.qase.api.services;

import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.milestones.Milestone;
import io.qase.api.models.v1.milestones.Milestones;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public interface MilestoneService {
    /**
     * This method allows to retrieve all milestones stored in selected project.
     *
     * @param projectCode Project code
     * @param limit       A number of milestones in result set
     * @param offset      How many milestones should be skipped
     * @return
     */
    Milestones getAll(String projectCode, int limit, int offset, RouteFilter filter);

    Milestones getAll(String projectCode, RouteFilter filter);

    Milestones getAll(String projectCode);

    Milestone get(String projectCode, long milestoneId);

    /**
     * This method is used to create a new milestone through API.
     *
     * @param projectCode Project code
     * @param title       Milestone title
     * @param description Milestone description
     * @return
     */
    long create(String projectCode, String title, String description);

    /**
     * This method is used to create a new milestone through API.
     *
     * @param projectCode Project code
     * @param title       Milestone title
     * @return
     */
    long create(String projectCode, String title);

    /**
     * This method completely deletes a milestone from repository
     *
     * @param projectCode Project code
     * @param id          Milestone id
     * @return
     */
    boolean delete(String projectCode, long id);

    /**
     * This method is used to update a milestone through API. You should provide an object with a list of fields you want to update in a payload.
     *
     * @param projectCode Project code
     * @param id          Milestone id
     * @param title       Milestone title
     * @param description Milestone description
     * @return
     */
    long update(String projectCode, long id, String title, String description);

    /**
     * This method is used to update a milestone through API. You should provide an object with a list of fields you want to update in a payload.
     *
     * @param projectCode Project code
     * @param id          Milestone id
     * @param title       Milestone title
     * @return
     */
    long update(String projectCode, long id, String title);

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
         * String that will be used to search by name
         *
         * @param search
         * @return
         */
        public Filter search(String search) {
            filters.put(Filters.search, search);
            return this;
        }
    }
}
