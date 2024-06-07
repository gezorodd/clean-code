package org.example.filter;

public interface Filter<T> {
    boolean accept(T value);
}
