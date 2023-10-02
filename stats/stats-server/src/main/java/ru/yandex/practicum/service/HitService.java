package ru.yandex.practicum.service;

import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.ViewStats;
import ru.yandex.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    EndpointHit saveHit(EndpointHitDto hitEndpointDto);

    List<ViewStats> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uriList, Boolean unique);
}