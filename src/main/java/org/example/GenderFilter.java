package org.example;

public class GenderFilter implements Filter<Gender> {
    private final Gender expectedValue;

    public GenderFilter(Gender expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Override
    public boolean match(Gender value) {
        return this.expectedValue == value;
    }
}
