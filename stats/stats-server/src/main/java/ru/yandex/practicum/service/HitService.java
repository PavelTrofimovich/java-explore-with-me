package ru.yandex.practicum.service;

import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.ViewStats;

import java.util.List;

public interface HitService {
    void saveHit(EndpointHitDto hitEndpointDto);

    List<ViewStats> getStatistics(String start, String end, List<String> uriList, Boolean unique);
}