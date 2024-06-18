package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserService {
    private static UserService instance;

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private List<User> users;

    private UserService() {

    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> getAllUsers() {
        if (this.users != null) {
            return this.users;
        }
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("users.csv");
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        UserMapper userMapper = new UserMapper();
        return this.users = reader.lines()
            .map(userMapper::mapUser)
            .collect(Collectors.toList());
    }

    public List<User> findUsers(UserSearchFilter filter, UserSearchType searchType) {
        List<User> allUsers = this.getAllUsers();
        if (allUsers == null) {
            return null;
        }
        List<User> users = new ArrayList<>();
        for (User user : allUsers) {
            boolean checkLogin = checkUserInfo(user.getLogin(), filter.getLoginFilter());
            boolean checkFirstName = checkUserInfo(user.getFirstName(), filter.getFirstNameFilter());
            boolean checkLastName = checkUserInfo(user.getLastName(), filter.getLastNameFilter());

            boolean checkBirthDate = this.checkBirthDate(filter, user);

            boolean checkGender;
            if (filter.getGenderFilter() != null) {
                checkGender = user.getGender().equals(filter.getGenderFilter());
            } else {
                checkGender = true;
            }

            boolean check;
            if (searchType == UserSearchType.ALL_MATCH) {
                check = checkLogin && checkFirstName && checkLastName && checkBirthDate && checkGender;
            } else {
                check = checkLogin || checkFirstName || checkLastName || checkBirthDate || checkGender;
            }

            if (check) {
                users.add(user);
            }
        }
        return users;
    }

    private boolean checkUserInfo(String actualValue, StringFilter stringFilter) {
        if (stringFilter == null) {
            return true;
        }
        String fragment = stringFilter.getFragment();
        UserSearchMode mode = stringFilter.getMode();

        if (fragment == null || mode == null) {
            return true;
        }

        boolean ignoreCase = stringFilter.isIgnoreCase();
        switch (mode) {
            case EQUALS:
                if (ignoreCase) {
                    return actualValue.equalsIgnoreCase(fragment);
                } else {
                    return actualValue.equals(fragment);
                }
            case CONTAINS:
                if (ignoreCase) {
                    return actualValue.toUpperCase().contains(fragment.toUpperCase());
                } else {
                    return actualValue.contains(fragment);
                }
            case STARTS_WITH:
                if (ignoreCase) {
                    return actualValue.toUpperCase().startsWith(fragment.toUpperCase());
                } else {
                    return actualValue.startsWith(fragment);
                }
            default:
                return false;
        }
    }

    private boolean checkBirthDate(UserSearchFilter filter, User user) {
        boolean checkBirthDate;
        if (filter.getMinBirthDate() != null) {
            if (filter.getMaxBirthDate() != null) {
                checkBirthDate = user.getBirthDate().isAfter(filter.getMinBirthDate()) &&
                    user.getBirthDate().isBefore(filter.getMaxBirthDate());
            } else {
                checkBirthDate = user.getBirthDate().isAfter(filter.getMinBirthDate());
            }
        } else if (filter.getMaxBirthDate() != null) {
            checkBirthDate = user.getBirthDate().isBefore(filter.getMaxBirthDate());
        } else {
            checkBirthDate = true;
        }
        return checkBirthDate;
    }
}
