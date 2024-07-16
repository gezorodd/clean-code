package org.example;

public class StringFilter implements Filter<String> {

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

    @Override
    public boolean match(String value) {
        if (fragment == null || mode == null) {
            return true;
        }

        switch (mode) {
            case EQUALS:
                if (ignoreCase) {
                    return value.equalsIgnoreCase(fragment);
                } else {
                    return value.equals(fragment);
                }
            case CONTAINS:
                if (ignoreCase) {
                    return value.toUpperCase().contains(fragment.toUpperCase());
                } else {
                    return value.contains(fragment);
                }
            case STARTS_WITH:
                if (ignoreCase) {
                    return value.toUpperCase().startsWith(fragment.toUpperCase());
                } else {
                    return value.startsWith(fragment);
                }
            default:
                return false;
        }
    }
}
