package org.example.filter;

import java.util.Arrays;
import java.util.List;

public class CombinationFilter<T> implements Filter<T> {
    private final CombinationMode mode;
    private final List<Filter<T>> filters;

    private CombinationFilter(CombinationMode mode, List<Filter<T>> filters) {
        this.mode = mode;
        this.filters = filters;
    }

    @SafeVarargs
    public static <T> CombinationFilter<T> allMatch(Filter<T>... filters) {
        return new CombinationFilter<>(CombinationMode.ALL_MATCH, Arrays.asList(filters));
    }

    @SafeVarargs
    public static <T> CombinationFilter<T> anyMatch(Filter<T>... filters) {
        return new CombinationFilter<>(CombinationMode.ANY_MATCH, Arrays.asList(filters));
    }

    @Override
    public boolean accept(T value) {
        return switch (this.mode) {
            case ALL_MATCH -> this.filters.stream()
                    .allMatch(filter -> filter.accept(value));
            case ANY_MATCH -> this.filters.stream()
                    .anyMatch(filter -> filter.accept(value));
        };
    }
}
