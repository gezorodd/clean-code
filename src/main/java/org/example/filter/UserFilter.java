package org.example.filter;

import org.example.Gender;
import org.example.User;

import java.util.ArrayList;
import java.util.List;

public class UserFilter {
    private final CombinationMode combinationMode;
    private StringFilter loginFilter;
    private StringFilter firstNameFilter;
    private StringFilter lastNameFilter;
    private LocalDateFilter birthDateFilter;
    private Gender genderFilter;

    private UserFilter(CombinationMode combinationMode) {
        this.combinationMode = combinationMode;
    }

    public static UserFilter allMatch() {
        return new UserFilter(CombinationMode.ALL_MATCH);
    }

    public static UserFilter anyMatch() {
        return new UserFilter(CombinationMode.ANY_MATCH);
    }

    public UserFilter loginFilter(StringFilter loginFilter) {
        this.loginFilter = loginFilter;
        return this;
    }

    public UserFilter firstNameFilter(StringFilter firstNameFilter) {
        this.firstNameFilter = firstNameFilter;
        return this;
    }

    public UserFilter lastNameFilter(StringFilter lastNameFilter) {
        this.lastNameFilter = lastNameFilter;
        return this;
    }

    public UserFilter birthDateFilter(LocalDateFilter birthDateFilter) {
        this.birthDateFilter = birthDateFilter;
        return this;
    }

    public UserFilter genderFilter(Gender genderFilter) {
        this.genderFilter = genderFilter;
        return this;
    }

    public boolean accept(User user) {
        List<Boolean> results = new ArrayList<>();
        if (this.loginFilter != null) {
            results.add(this.loginFilter.accept(user.getLogin()));
        }
        if (this.firstNameFilter != null) {
            results.add(this.firstNameFilter.accept(user.getFirstName()));
        }
        if (this.lastNameFilter != null) {
            results.add(this.lastNameFilter.accept(user.getLastName()));
        }
        if (this.birthDateFilter != null) {
            results.add(this.birthDateFilter.accept(user.getBirthDate()));
        }
        if (this.genderFilter != null) {
            results.add(this.genderFilter.equals(user.getGender()));
        }
        return switch (this.combinationMode) {
            case ALL_MATCH -> results.stream().allMatch(result -> result);
            case ANY_MATCH -> results.stream().anyMatch(result -> result);
        };
    }
}
