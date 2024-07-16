package org.example;

public interface Filter<T> {
    boolean match(T value);
}
