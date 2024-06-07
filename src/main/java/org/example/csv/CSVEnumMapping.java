package org.example.csv;

import java.util.Map;
import java.util.function.BiConsumer;

public class CSVEnumMapping<T, D extends Enum<D>> implements CSVMapping<T> {
    private final int position;
    private final BiConsumer<T, D> setter;
    private final Map<String, D> valueMapping;

    public CSVEnumMapping(int position, BiConsumer<T, D> setter, Map<String, D> valueMapping) {
        this.position = position;
        this.setter = setter;
        this.valueMapping = valueMapping;
    }

    @Override
    public void apply(T item, CSVRecord record) {
        String rawValue = record.getValue(this.position, String.class);
        D value = this.valueMapping.get(rawValue);
        this.setter.accept(item, value);
    }
}
