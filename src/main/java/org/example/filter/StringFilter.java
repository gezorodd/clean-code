package org.example.filter;

public class StringFilter implements Filter<String> {
    private final StringMatchMode matchMode;
    private final String fragment;
    private final boolean ignoreCase;

    private StringFilter(StringMatchMode matchMode, String fragment, boolean ignoreCase) {
        this.matchMode = matchMode;
        this.fragment = fragment;
        this.ignoreCase = ignoreCase;
    }

    public static StringFilter isEqual(String value) {
        return new StringFilter(StringMatchMode.EQUALS, value, false);
    }

    public static StringFilter isEqualIgnoreCase(String value) {
        return new StringFilter(StringMatchMode.EQUALS, value, true);
    }

    public static StringFilter contains(String value) {
        return new StringFilter(StringMatchMode.CONTAINS, value, false);
    }

    @SuppressWarnings("unused")
    public static StringFilter containsIgnoreCase(String value) {
        return new StringFilter(StringMatchMode.CONTAINS, value, true);
    }

    @SuppressWarnings("unused")
    public static StringFilter startsWith(String value) {
        return new StringFilter(StringMatchMode.STARTS_WITH, value, false);
    }

    public static StringFilter startsWithIgnoreCase(String value) {
        return new StringFilter(StringMatchMode.STARTS_WITH, value, true);
    }

    @Override
    public boolean accept(String value) {
        String valueOperand = this.ignoreCase ? value.toUpperCase() : value;
        String fragmentOperand = this.ignoreCase ? this.fragment.toUpperCase() : this.fragment;
        return switch (this.matchMode) {
            case STARTS_WITH -> valueOperand.startsWith(fragmentOperand);
            case CONTAINS -> valueOperand.contains(fragmentOperand);
            case EQUALS -> valueOperand.equals(fragmentOperand);
        };
    }
}
