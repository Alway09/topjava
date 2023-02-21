package ru.javawebinar.topjava;

import static org.assertj.core.api.Assertions.assertThat;

public class MatcherUtil {

    private final String[] ignoredFields;

    public MatcherUtil(String... ignoredFields) {
        this.ignoredFields = ignoredFields;
    }

    public <T> void assertMatch(T actual, T expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields(ignoredFields).isEqualTo(expected);
    }

    public <T> void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields(ignoredFields).isEqualTo(expected);
    }
}
