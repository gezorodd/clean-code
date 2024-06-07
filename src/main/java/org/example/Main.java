package org.example;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        UserService userService = UserService.getInstance();

        UserSearchFilter userSearchFilter = new UserSearchFilter();
        userSearchFilter.setMinBirthDate(LocalDate.of(2000, 1, 1));
        userSearchFilter.setMaxBirthDate(LocalDate.of(2010, 1, 1));
        userSearchFilter.setGenderFilter("M");
        userSearchFilter.setLoginFragment("3");
        userSearchFilter.setLoginMode(UserSearchMode.CONTAINS);

        userSearchFilter.setFirstNameFragment("Malvin");
        userSearchFilter.setFirstNameMode(UserSearchMode.EQUALS);
        userSearchFilter.setFirstNameIgnoreCase(true);

        userSearchFilter.setLastNameFragment("Cira");
        userSearchFilter.setLastNameMode(UserSearchMode.STARTS_WITH);
        userSearchFilter.setLastNameIgnoreCase(true);

        List<User> users = userService.findUsers(userSearchFilter, UserSearchType.ALL_MATCH);
        System.out.println(
                users.stream()
                        .map(User::toString)
                        .collect(Collectors.joining("\n"))
        );
    }
}
