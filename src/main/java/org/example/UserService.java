package org.example;

import org.example.filter.Filter;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private static UserService instance;
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
            this.users = new UserListLoader().loadUsers();
        }
        return this.users;
    }

    public List<User> findUsers(Filter<User> filter) {
        return this.getAllUsers().stream()
                .filter(filter::accept)
                .collect(Collectors.toList());
    }
}
