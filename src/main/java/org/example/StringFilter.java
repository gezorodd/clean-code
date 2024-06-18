package org.example;

public class StringFilter {

    private String fragment;
    private UserSearchMode mode;
    private boolean ignoreCase;

    public String getFragment() {
        return fragment;
    }

    public StringFilter fragment(String fragment) {
        this.fragment = fragment;
        return this;
    }

    public UserSearchMode getMode() {
        return mode;
    }

    public StringFilter mode(UserSearchMode mode) {
        this.mode = mode;
        return this;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public StringFilter ignoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
        return this;
    }
}
