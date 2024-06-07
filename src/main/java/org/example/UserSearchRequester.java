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

public class UserSearchRequester {

    public static final String PARAM_LOGIN = "login";
    public static final String PARAM_LOGIN_MODE = "loginMode";
    public static final String PARAM_LOGIN_IGNORE_CASE = "loginIgnoreCase";
    public static final String PARAM_FIRST_NAME = "firstName";
    public static final String PARAM_FIRST_NAME_MODE = "firstNameMode";
    public static final String PARAM_FIRST_NAME_IGNORE_CASE = "firstNameIgnoreCase";
    public static final String PARAM_LAST_NAME = "lastName";
    public static final String PARAM_LAST_NAME_MODE = "lastNameMode";
    public static final String PARAM_LAST_NAME_IGNORE_CASE = "lastNameIgnoreCase";
    public static final String PARAM_MIN_DATE = "minDate";
    public static final String PARAM_MAX_DATE = "maxDate";
    public static final String PARAM_GENDER = "gender";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static UserSearchRequester instance;

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private List<User> users;

    private UserSearchRequester() {

    }

    public static UserSearchRequester getInstance() {
        if (instance == null) {
            instance = new UserSearchRequester();
        }
        return instance;
    }

    public void fillUserSearchContainer(UserSearchContainer userSearchContainer, int searchType) {

        // Fill users
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
                return;
            }
        }

        // Find the users
        for (User user : this.users) {
            boolean checkLogin = checkUserInfo(user.getLogin(), userSearchContainer, PARAM_LOGIN, PARAM_LOGIN_MODE,
                    PARAM_LOGIN_IGNORE_CASE);

            boolean checkFirstName = checkUserInfo(user.getFirstName(), userSearchContainer, PARAM_FIRST_NAME,
                    PARAM_FIRST_NAME_MODE, PARAM_FIRST_NAME_IGNORE_CASE);

            boolean checkLastName = checkUserInfo(user.getLastName(), userSearchContainer, PARAM_LAST_NAME,
                    PARAM_LAST_NAME_MODE, PARAM_LAST_NAME_IGNORE_CASE);

            // Check birth date
            boolean checkBirthDate;
            try {
                String minDate = userSearchContainer.getParam(PARAM_MIN_DATE);
                String maxDate = userSearchContainer.getParam(PARAM_MAX_DATE);
                if (minDate != null) {
                    if (maxDate != null) {
                        checkBirthDate = user.getBirthDate().isAfter(parseDate(minDate)) &&
                                user.getBirthDate().isBefore(parseDate(maxDate));
                    } else {
                        checkBirthDate = user.getBirthDate().isAfter(parseDate(minDate));
                    }
                } else if (maxDate != null) {
                    checkBirthDate = user.getBirthDate().isBefore(parseDate(maxDate));
                } else {
                    checkBirthDate = true;
                }
            } catch (ParseException e) {
                return;
            }

            boolean checkGender;
            if (userSearchContainer.getParam(PARAM_GENDER) != null) {
                checkGender = user.getGender().equals(userSearchContainer.getParam(PARAM_GENDER));
            } else {
                checkGender = true;
            }

            if ((searchType == UserSearchContainer.ALL_MATCH_TYPE && checkLogin && checkFirstName && checkLastName && checkBirthDate && checkGender) ||
                    (searchType == UserSearchContainer.ANY_MATCH_TYPE && (checkLogin || checkFirstName || checkLastName || checkBirthDate || checkGender))) {
                userSearchContainer.addUser(user);
            }
        }
    }

    private boolean checkUserInfo(String value, UserSearchContainer userSearchContainer,
                                  String infoParam, String infoModeParam, String infoIgnoreCaseParam) {
        String info = userSearchContainer.getParam(infoParam);
        int infoMode = userSearchContainer.getIntParam(infoModeParam);
        boolean infoIgnoreCase = userSearchContainer.getBooleanParam(infoIgnoreCaseParam);
        if (info != null && infoMode > 0) {
            switch (infoMode) {
                case UserSearchContainer.STARTS_WITH_MODE:
                    return applyUpperCase(value, infoIgnoreCase).startsWith(applyUpperCase(info, infoIgnoreCase));
                case UserSearchContainer.CONTAINS_MODE:
                    return applyUpperCase(value, infoIgnoreCase).contains(applyUpperCase(info, infoIgnoreCase));
                case UserSearchContainer.EQUALS_MODE:
                    return applyUpperCase(value, infoIgnoreCase).equals(applyUpperCase(info, infoIgnoreCase));
                default:
                    return false;
            }
        } else {
            return true;
        }
    }

    private String applyUpperCase(String value, boolean doApply) {
        if (doApply) {
            return value.toUpperCase();
        } else {
            return value;
        }
    }

    private LocalDate parseDate(String date) throws ParseException {
        return DATE_FORMAT.parse(date).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
