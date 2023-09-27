package ru.yandex.practicum.exceptions;

import lombok.*;

@RequiredArgsConstructor
public class ErrorResponse {
    @Getter
    private final String error;
}
