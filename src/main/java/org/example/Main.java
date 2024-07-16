package org.example;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        UserService userService = UserService.getInstance();

        UserSearchFilter userSearchFilter = new UserSearchFilter(UserMatchMode.ALL_MATCH);
        userSearchFilter.setBirthDateFilter(
            new LocalDateFilter(
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2010, 1, 1)
            )
        );
        userSearchFilter.setGenderFilter(new GenderFilter(Gender.MALE));

        userSearchFilter.setLoginFilter(
            new StringFilter()
                .fragment("3")
                .mode(UserSearchMode.CONTAINS)
        );
        userSearchFilter.setFirstNameFilter(
            new StringFilter()
                .fragment("Malvin")
                .mode(UserSearchMode.EQUALS)
                .ignoreCase(true)
        );
        userSearchFilter.setLastNameFilter(
            new StringFilter()
                .fragment("Cira")
                .mode(UserSearchMode.STARTS_WITH)
                .ignoreCase(true)
        );

        List<User> users = userService.findUsers(userSearchFilter);
        System.out.println(
            users.stream()
                .map(User::toString)
                .collect(Collectors.joining("\n"))
        );
    }
}
