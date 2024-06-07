package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class UserListLoader {
    private static final SimpleDateFormat BIRTH_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public List<User> loadUsers() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("users.csv");
        if (inputStream == null) {
            throw new RuntimeException("Could not get user list resource");
        }

        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            List<User> users = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                User user = this.parseUserLine(line);
                users.add(user);
                line = reader.readLine();
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException("Could not load users", e);
        }
    }

    private User parseUserLine(String userLine) {
        String[] fields = userLine.split(",");
        User user = new User();
        user.setId(Long.parseLong(fields[0]));
        user.setLogin(fields[1]);
        user.setFirstName(fields[2]);
        user.setLastName(fields[3]);
        user.setBirthDate(this.parseBirthDate(fields[4]));
        user.setGender(this.parseGender(fields[5]));
        return user;
    }

    private LocalDate parseBirthDate(String input) {
        try {
            return BIRTH_DATE_FORMAT.parse(input).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } catch (ParseException e) {
            throw new RuntimeException("Could not parse birthdate for input " + input, e);
        }
    }

    private Gender parseGender(String input) {
        return switch (input) {
            case "M" -> Gender.MALE;
            case "F" -> Gender.FEMALE;
            default -> throw new RuntimeException("Invalid gender: " + input);
        };
    }
}
