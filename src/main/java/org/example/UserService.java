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
import java.util.logging.Logger;

public class UserService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
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
        if (this.users == null) {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("users.csv");
            InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            try {
                this.users = new ArrayList<>();
                for (String line; (line = reader.readLine()) != null; ) {
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
                        this.users.add(user);
                    } catch (ParseException e) {
                        throw new RuntimeException("Problem with birthdate");
                    }
                }
            } catch (IOException e) {
                this.logger.severe("Could not load users");
                return null;
            }
        }
        return this.users;
    }

    public List<User> findUsers(UserSearchFilter filter, UserSearchType searchType) {
        List<User> users = new ArrayList<>();
        List<User> allUsers = this.getAllUsers();
        if (allUsers == null) {
            return null;
        }
        for (User user : allUsers) {
            boolean checkLogin = checkUserInfo(user.getLogin(), filter.getLoginFragment(),
                    filter.getLoginMode(), filter.isLoginIgnoreCase());
            boolean checkFirstName = checkUserInfo(user.getFirstName(), filter.getFirstNameFragment(),
                    filter.getFirstNameMode(), filter.isFirstNameIgnoreCase());
            boolean checkLastName = checkUserInfo(user.getLastName(), filter.getLastNameFragment(),
                    filter.getLastNameMode(), filter.isLastNameIgnoreCase());

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

    private boolean checkUserInfo(String actualValue, String fragment, UserSearchMode mode, boolean ignoreCase) {
        if (fragment == null || mode == null) {
            return true;
        }
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
