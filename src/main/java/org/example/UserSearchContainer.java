package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserSearchContainer {
    public static final int ALL_MATCH_TYPE = 1;
    public static final int ANY_MATCH_TYPE = 2;

    public static final int STARTS_WITH_MODE = 1;
    public static final int CONTAINS_MODE = 2;
    public static final int EQUALS_MODE = 3;


    private Map<String, String> params;
    private List<User> users = new ArrayList<>();

    public UserSearchContainer(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public UserSearchContainer params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public String getParam(String name) {
        return this.params.get(name);
    }

    public int getIntParam(String name) {
        return Integer.parseInt(this.getParam(name));
    }

    public boolean getBooleanParam(String name) {
        return Boolean.parseBoolean(this.getParam(name));
    }
}
