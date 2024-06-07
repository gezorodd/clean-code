package org.example.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class CSVParser<T> {

    private final Supplier<T> itemConstructor;
    private final List<CSVMapping<T>> fieldMappings;

    @SafeVarargs
    public CSVParser(Supplier<T> recordSupplier, CSVMapping<T>... fieldMappings) {
        this.itemConstructor = recordSupplier;
        this.fieldMappings = Arrays.asList(fieldMappings);
    }

    public List<T> parserItems(InputStream inputStream) {
        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            List<T> items = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                CSVRecord record = CSVRecord.parseLine(line);
                T item = this.createItem(record);
                items.add(item);
                line = reader.readLine();
            }
            return items;
        } catch (IOException e) {
            throw new RuntimeException("Could not load users", e);
        }
    }

    private T createItem(CSVRecord record) {
        T item = this.itemConstructor.get();
        this.fieldMappings
                .forEach(fieldMapping -> fieldMapping.apply(item, record));
        return item;
    }
}
