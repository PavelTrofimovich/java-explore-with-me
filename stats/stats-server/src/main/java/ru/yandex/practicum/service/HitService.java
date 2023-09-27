package ru.yandex.practicum.service;

import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.ViewStats;
import ru.yandex.practicum.model.EndpointHit;

import java.util.List;

public interface HitService {
    EndpointHit saveHit(EndpointHitDto hitEndpointDto);

    List<ViewStats> getStatistics(String start, String end, List<String> uriList, Boolean unique);
}