package org.example.filter;

import java.time.LocalDate;

public class LocalDateFilter implements Filter<LocalDate> {
    private final LocalDate min;
    private final LocalDate max;

    private LocalDateFilter(LocalDate min, LocalDate max) {
        this.min = min;
        this.max = max;
    }

    public static LocalDateFilter min(LocalDate value) {
        return new LocalDateFilter(value, null);
    }

    public static LocalDateFilter max(LocalDate value) {
        return new LocalDateFilter(null, value);
    }

    public static LocalDateFilter between(LocalDate value1, LocalDate value2) {
        return new LocalDateFilter(value1, value2);
    }

    @Override
    public boolean accept(LocalDate value) {
        boolean checkMin = this.min == null || value.isAfter(this.min);
        boolean checkMax = this.max == null || value.isBefore(this.max);
        return checkMin && checkMax;
    }
}
