package org.example.filter;

import java.util.function.Function;

public class DelegateProviderFilter<T, D> implements Filter<T> {
    private final Function<T, D> delegatedValueProvider;
    private final Filter<D> delegatedFilter;

    public DelegateProviderFilter(Function<T, D> delegatedValueProvider, Filter<D> delegatedFilter) {
        this.delegatedValueProvider = delegatedValueProvider;
        this.delegatedFilter = delegatedFilter;
    }

    public static <T, D> DelegateProviderFilter<T, D> provided(Function<T, D> delegatedValueProvider, Filter<D> delegatedFilter) {
        return new DelegateProviderFilter<>(delegatedValueProvider, delegatedFilter);
    }

    @Override
    public boolean accept(T value) {
        D delegatedValue = this.delegatedValueProvider.apply(value);
        return this.delegatedFilter.accept(delegatedValue);
    }
}
