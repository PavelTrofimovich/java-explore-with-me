package ru.yandex.practicum.exceptions;

public class InvalidValidationException extends RuntimeException {

    public InvalidValidationException(String message) {
        super(message);
    }
}
