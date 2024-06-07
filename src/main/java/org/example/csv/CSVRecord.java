package org.example.csv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class CSVRecord {
    private static final SimpleDateFormat LOCAL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final String[] fields;

    CSVRecord(String[] fields) {
        this.fields = fields;
    }

    static CSVRecord parseLine(String line) {
        return new CSVRecord(line.split(","));
    }

    @SuppressWarnings("unchecked")
    <T> T getValue(int position, Class<T> type) {
        if (position >= this.fields.length) {
            return null;
        }
        String rawValue = this.fields[position];
        if (type == String.class) {
            return (T) rawValue;
        } else if (type == Integer.class) {
            return (T) Integer.valueOf(rawValue);
        } else if (type == Long.class) {
            return (T) Long.valueOf(rawValue);
        } else if (type == Float.class) {
            return (T) Float.valueOf(rawValue);
        } else if (type == Double.class) {
            return (T) Double.valueOf(rawValue);
        } else if (type == LocalDate.class) {
            return (T) this.parseLocalDate(rawValue);
        } else {
            throw new RuntimeException("Unsupported value type: " + type);
        }
    }

    private LocalDate parseLocalDate(String input) {
        try {
            return LOCAL_DATE_FORMAT.parse(input).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } catch (ParseException e) {
            throw new RuntimeException("Could not parse local date for input " + input, e);
        }
    }
}
