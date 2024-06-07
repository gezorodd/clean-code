package org.example.filter;

import org.example.Gender;

public class GenderFilter implements Filter<Gender> {
    private final Gender value;

    private GenderFilter(Gender value) {
        this.value = value;
    }

    public static GenderFilter isEqual(Gender value) {
        return new GenderFilter(value);
    }

    @Override
    public boolean accept(Gender value) {
        return this.value.equals(value);
    }
}
