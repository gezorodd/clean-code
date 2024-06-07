package org.example;

import org.example.filter.Filter;
import org.example.filter.StringFilter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.filter.CombinationFilter.allMatch;
import static org.example.filter.CombinationFilter.anyMatch;
import static org.example.filter.DelegateProviderFilter.provided;
import static org.example.filter.GenderFilter.isEqual;
import static org.example.filter.LocalDateFilter.between;
import static org.example.filter.StringFilter.*;

public class Main {

    public static void main(String[] args) {
        UserService userService = UserService.getInstance();

        Filter<User> userFilter = anyMatch(
                allMatch(
                        provided(User::getBirthDate, between(
                                LocalDate.of(2000, 1, 1),
                                LocalDate.of(2010, 1, 1)
                        )),
                        provided(User::getGender, isEqual(Gender.MALE)),
                        provided(User::getLogin, contains("3")),
                        provided(User::getFirstName, isEqualIgnoreCase("Malvin")),
                        provided(User::getLastName, startsWithIgnoreCase("Cira"))
                ),
                provided(User::getFirstName, StringFilter.isEqual("Leonard"))
        );

        List<User> users = userService.findUsers(userFilter);
        System.out.println(
                users.stream()
                        .map(User::toString)
                        .collect(Collectors.joining("\n"))
        );
    }
}
