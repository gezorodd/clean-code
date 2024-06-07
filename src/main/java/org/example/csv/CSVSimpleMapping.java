package org.example.csv;

import java.util.function.BiConsumer;

public class CSVSimpleMapping<T, D> implements CSVMapping<T> {
    private final int position;
    private final Class<D> type;
    private final BiConsumer<T, D> setter;

    public CSVSimpleMapping(int position, Class<D> type, BiConsumer<T, D> setter) {
        this.position = position;
        this.type = type;
        this.setter = setter;
    }

    @Override
    public void apply(T item, CSVRecord record) {
        D value = record.getValue(this.position, this.type);
        this.setter.accept(item, value);
    }
}
