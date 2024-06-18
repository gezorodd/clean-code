package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class UserMapper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public User mapUser(String line) {
        String[] fields = line.split(",");
        try {
            User user = new User();
            user.setId(Long.parseLong(fields[0]));
            user.setLogin(fields[1]);
            user.setFirstName(fields[2]);
            user.setLastName(fields[3]);
            LocalDate birthDate = DATE_FORMAT.parse(fields[4]).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
            user.setBirthDate(birthDate);
            user.setGender(fields[5]);
            return user;
        } catch (ParseException e) {
            throw new RuntimeException("Problem with birthdate");
        }
    }
}
