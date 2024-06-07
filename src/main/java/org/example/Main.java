package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        UserSearchRequester userSearchRequester = UserSearchRequester.getInstance();

        Map<String, String> params = new HashMap<>();
        UserSearchContainer userSearchContainer = new UserSearchContainer(params);

        params.put(UserSearchRequester.PARAM_MIN_DATE, "2000-01-01");
        params.put(UserSearchRequester.PARAM_MAX_DATE, "2010-01-01");
        params.put(UserSearchRequester.PARAM_GENDER, "M");

        params.put(UserSearchRequester.PARAM_LOGIN, "3");
        params.put(UserSearchRequester.PARAM_LOGIN_MODE, UserSearchContainer.CONTAINS_MODE + "");

        params.put(UserSearchRequester.PARAM_FIRST_NAME, "Malvin");
        params.put(UserSearchRequester.PARAM_FIRST_NAME_MODE, UserSearchContainer.EQUALS_MODE + "");
        params.put(UserSearchRequester.PARAM_FIRST_NAME_IGNORE_CASE, "true");

        params.put(UserSearchRequester.PARAM_LAST_NAME, "Cira");
        params.put(UserSearchRequester.PARAM_LAST_NAME_MODE, UserSearchContainer.STARTS_WITH_MODE + "");
        params.put(UserSearchRequester.PARAM_LAST_NAME_IGNORE_CASE, "true");


        userSearchRequester.fillUserSearchContainer(userSearchContainer, UserSearchContainer.ALL_MATCH_TYPE);

        System.out.println(
                userSearchContainer.getUsers().stream()
                        .map(User::toString)
                        .collect(Collectors.joining("\n"))
        );
    }
}
