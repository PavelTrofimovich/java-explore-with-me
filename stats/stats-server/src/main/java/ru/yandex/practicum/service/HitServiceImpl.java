package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.ViewStats;
import ru.yandex.practicum.exceptions.InvalidValidationException;
import ru.yandex.practicum.mapper.Mapper;
import ru.yandex.practicum.model.EndpointHit;
import ru.yandex.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final StatsRepository statRepository;

    @Transactional
    @Override
    public EndpointHit saveHit(EndpointHitDto endpointHitDto) {
        return statRepository.save(Mapper.toEndpointHit(endpointHitDto));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ViewStats> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (start.isAfter(end)) {
            log.error("Начальная дата не может быть позже конечной");
            throw new InvalidValidationException("Начальная дата не может быть позже конечной");
        }

        if (uris == null || uris.isEmpty()) {
            if (Boolean.TRUE.equals(unique)) {
                return statRepository.findUniqueStatsForAll(start, end);
            } else {
                return statRepository.findStatsForAll(start, end);
            }
        } else {
            if (Boolean.TRUE.equals(unique)) {
                return statRepository.findUniqueStats(start, end, uris);
            } else {
                return statRepository.findStats(start, end, uris);
            }
        }
    }
}
