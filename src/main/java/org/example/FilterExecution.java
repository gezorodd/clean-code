package org.example;

public class FilterExecution<T> {
    private final Filter<T> filter;
    private final T value;

    public FilterExecution(Filter<T> filter, T value) {
        this.filter = filter;
        this.value = value;
    }

    public boolean match() {
        return filter.match(value);
    }
}
