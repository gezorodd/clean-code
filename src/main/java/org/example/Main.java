package org.example;

import org.example.filter.LocalDateFilter;
import org.example.filter.StringFilter;
import org.example.filter.UserFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        UserService userService = UserService.getInstance();

        UserFilter userFilter = UserFilter.allMatch()
                .birthDateFilter(LocalDateFilter.between(
                        LocalDate.of(2000, 1, 1),
                        LocalDate.of(2010, 1, 1)
                ))
                .genderFilter(Gender.MALE)
                .loginFilter(StringFilter.contains("3"))
                .firstNameFilter(StringFilter.equalsIgnoreCase("Malvin"))
                .lastNameFilter(StringFilter.startsWithIgnoreCase("Cira"));

        List<User> users = userService.findUsers(userFilter);
        System.out.println(
                users.stream()
                        .map(User::toString)
                        .collect(Collectors.joining("\n"))
        );
    }
}
