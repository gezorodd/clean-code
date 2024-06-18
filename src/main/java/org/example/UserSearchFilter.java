package org.example;

import java.time.LocalDate;

public class UserSearchFilter {
    
    private StringFilter loginFilter;
    private StringFilter firstNameFilter;
    private StringFilter lastNameFilter;
    private LocalDate minBirthDate;
    private LocalDate maxBirthDate;
    private String genderFilter;

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

    public LocalDate getMinBirthDate() {
        return minBirthDate;
    }

    public void setMinBirthDate(LocalDate minBirthDate) {
        this.minBirthDate = minBirthDate;
    }

    public LocalDate getMaxBirthDate() {
        return maxBirthDate;
    }

    public void setMaxBirthDate(LocalDate maxBirthDate) {
        this.maxBirthDate = maxBirthDate;
    }

    public String getGenderFilter() {
        return genderFilter;
    }

    public void setGenderFilter(String genderFilter) {
        this.genderFilter = genderFilter;
    }
}
