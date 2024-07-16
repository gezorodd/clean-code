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

    public List<User> findUsers(UserSearchFilter filter) {
        List<User> allUsers = this.getAllUsers();
        if (allUsers == null) {
            return null;
        }
        List<User> users = new ArrayList<>();
        for (User user : allUsers) {
            if (filter.match(user)) {
                users.add(user);
            }
        }
        return users;
    }
}
