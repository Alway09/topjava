package ru.javawebinar.topjava.util.exception;

import org.springframework.lang.NonNull;

import java.util.List;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final List<String> details;

    public ErrorInfo(CharSequence url, ErrorType type, @NonNull List<String> details) {
        this.url = url.toString();
        this.type = type;
        this.details = details;
    }
}