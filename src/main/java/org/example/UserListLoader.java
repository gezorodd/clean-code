package org.example;

import org.example.csv.CSVEnumMapping;
import org.example.csv.CSVParser;
import org.example.csv.CSVSimpleMapping;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class UserListLoader {
    private final CSVParser<User> csvParser;

    public UserListLoader() {
        this.csvParser = new CSVParser<>(User::new,
                new CSVSimpleMapping<>(0, Long.class, User::setId),
                new CSVSimpleMapping<>(1, String.class, User::setLogin),
                new CSVSimpleMapping<>(2, String.class, User::setFirstName),
                new CSVSimpleMapping<>(3, String.class, User::setLastName),
                new CSVSimpleMapping<>(4, LocalDate.class, User::setBirthDate),
                new CSVEnumMapping<>(5, User::setGender, Map.of(
                        "M", Gender.MALE,
                        "F", Gender.FEMALE
                ))
        );
    }

    public List<User> loadUsers() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classloader.getResourceAsStream("users.csv")) {
            if (inputStream == null) {
                throw new RuntimeException("Could not get user list resource");
            }
            return this.csvParser.parserItems(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load user list", e);
        }
    }
}
