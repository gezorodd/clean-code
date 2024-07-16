package org.example;

import java.time.LocalDate;

public class LocalDateFilter implements Filter<LocalDate> {
    
    private LocalDate min;
    private LocalDate max;

    public LocalDateFilter(LocalDate min, LocalDate max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean match(LocalDate value) {
        boolean checkBirthDate;
        if (min != null) {
            if (max != null) {
                checkBirthDate = value.isAfter(min) &&
                    value.isBefore(max);
            } else {
                checkBirthDate = value.isAfter(min);
            }
        } else if (max != null) {
            checkBirthDate = value.isBefore(max);
        } else {
            checkBirthDate = true;
        }
        return checkBirthDate;
    }
}
