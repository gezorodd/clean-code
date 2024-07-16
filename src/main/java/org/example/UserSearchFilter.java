package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserSearchFilter {

    private final UserMatchMode matchMode;
    private StringFilter loginFilter;
    private StringFilter firstNameFilter;
    private StringFilter lastNameFilter;
    private LocalDateFilter birthDateFilter;
    private GenderFilter genderFilter;

    public UserSearchFilter(UserMatchMode matchMode) {
        this.matchMode = matchMode;
    }

    public UserMatchMode getMatchMode() {
        return matchMode;
    }

    public StringFilter getLoginFilter() {
        return loginFilter;
    }

    public void setLoginFilter(StringFilter loginFilter) {
        this.loginFilter = loginFilter;
    }

    public StringFilter getFirstNameFilter() {
        return firstNameFilter;
    }

    public void setFirstNameFilter(StringFilter firstNameFilter) {
        this.firstNameFilter = firstNameFilter;
    }

    public StringFilter getLastNameFilter() {
        return lastNameFilter;
    }

    public void setLastNameFilter(StringFilter lastNameFilter) {
        this.lastNameFilter = lastNameFilter;
    }

    public LocalDateFilter getBirthDateFilter() {
        return birthDateFilter;
    }

    public void setBirthDateFilter(LocalDateFilter birthDateFilter) {
        this.birthDateFilter = birthDateFilter;
    }

    public GenderFilter getGenderFilter() {
        return genderFilter;
    }

    public void setGenderFilter(GenderFilter genderFilter) {
        this.genderFilter = genderFilter;
    }
    
    public boolean match(User user) {
        List<FilterExecution<?>> filterExecutions = new ArrayList<>();
        if (this.loginFilter != null) {
            var filterExecution = new FilterExecution<>(this.loginFilter, user.getLogin());
            filterExecutions.add(filterExecution);
        }
        if (this.firstNameFilter != null) {
            var filterExecution = new FilterExecution<>(this.firstNameFilter, user.getFirstName());
            filterExecutions.add(filterExecution);
        }
        if (this.lastNameFilter != null) {
            var filterExecution = new FilterExecution<>(this.lastNameFilter, user.getLastName());
            filterExecutions.add(filterExecution);
        }
        if (this.birthDateFilter != null) {
            var filterExecution = new FilterExecution<>(this.birthDateFilter, user.getBirthDate());
            filterExecutions.add(filterExecution);
        }
        if (this.genderFilter != null) {
            var filterExecution = new FilterExecution<>(this.genderFilter, user.getGender());
            filterExecutions.add(filterExecution);
        }

        boolean check;
        if (getMatchMode() == UserMatchMode.ALL_MATCH) {
            check = checkLogin && checkFirstName && checkLastName && checkBirthDate && checkGender;
        } else {
            check = checkLogin || checkFirstName || checkLastName || checkBirthDate || checkGender;
        }
        return check;
    }

    private boolean checkUserInfo(String actualValue, StringFilter stringFilter) {
        if (stringFilter == null) {
            return true;
        }
        return stringFilter.match(actualValue);
    }
}
