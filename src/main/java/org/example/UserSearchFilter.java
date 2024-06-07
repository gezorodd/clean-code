package org.example;

import java.time.LocalDate;

public class UserSearchFilter {
    
    private String loginFragment;
    private UserSearchMode loginMode;
    private boolean loginIgnoreCase;
    private String firstNameFragment;
    private UserSearchMode firstNameMode;
    private boolean firstNameIgnoreCase;
    private String lastNameFragment;
    private UserSearchMode lastNameMode;
    private boolean lastNameIgnoreCase;
    private LocalDate minBirthDate;
    private LocalDate maxBirthDate;
    private String genderFilter;

    public String getLoginFragment() {
        return loginFragment;
    }

    public void setLoginFragment(String loginFragment) {
        this.loginFragment = loginFragment;
    }

    public UserSearchMode getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(UserSearchMode loginMode) {
        this.loginMode = loginMode;
    }

    public boolean isLoginIgnoreCase() {
        return loginIgnoreCase;
    }

    public void setLoginIgnoreCase(boolean loginIgnoreCase) {
        this.loginIgnoreCase = loginIgnoreCase;
    }

    public String getFirstNameFragment() {
        return firstNameFragment;
    }

    public void setFirstNameFragment(String firstNameFragment) {
        this.firstNameFragment = firstNameFragment;
    }

    public UserSearchMode getFirstNameMode() {
        return firstNameMode;
    }

    public void setFirstNameMode(UserSearchMode firstNameMode) {
        this.firstNameMode = firstNameMode;
    }

    public boolean isFirstNameIgnoreCase() {
        return firstNameIgnoreCase;
    }

    public void setFirstNameIgnoreCase(boolean firstNameIgnoreCase) {
        this.firstNameIgnoreCase = firstNameIgnoreCase;
    }

    public String getLastNameFragment() {
        return lastNameFragment;
    }

    public void setLastNameFragment(String lastNameFragment) {
        this.lastNameFragment = lastNameFragment;
    }

    public UserSearchMode getLastNameMode() {
        return lastNameMode;
    }

    public void setLastNameMode(UserSearchMode lastNameMode) {
        this.lastNameMode = lastNameMode;
    }

    public boolean isLastNameIgnoreCase() {
        return lastNameIgnoreCase;
    }

    public void setLastNameIgnoreCase(boolean lastNameIgnoreCase) {
        this.lastNameIgnoreCase = lastNameIgnoreCase;
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
