package org.example.csv;

public interface CSVMapping<T> {
    void apply(T item, CSVRecord record);
}
