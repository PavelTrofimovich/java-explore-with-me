package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.ViewStats;
import ru.yandex.practicum.mapper.Mapper;
import ru.yandex.practicum.model.EndpointHit;
import ru.yandex.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final StatsRepository statRepository;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    @Override
    public EndpointHit saveHit(EndpointHitDto endpointHitDto) {
        return statRepository.save(Mapper.toEndpointHit(endpointHitDto));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ViewStats> getStatistics(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startTimeFormat;
        LocalDateTime endTimeFormat;
        try {
            startTimeFormat = LocalDateTime.parse(start, DTF);
            endTimeFormat = LocalDateTime.parse(end, DTF);
        } catch (DateTimeParseException e) {
            return null;//throw new RuntimeException("Неверный формат дат");
        }

        if (startTimeFormat.isAfter(endTimeFormat)) {
            //throw new InvalidValidationException("Start time must be before end time!");
        }

        if (uris == null || uris.isEmpty()) {
            if (Boolean.TRUE.equals(unique)) {
                return statRepository.findUniqueStatsForAll(startTimeFormat, endTimeFormat)
                        .stream()
                        .map(viewStats -> new ViewStats(viewStats.getApp(), viewStats.getUri(), viewStats.getHits()))
                        .collect(Collectors.toList());
            } else {
                return statRepository.findStatsForAll(startTimeFormat, endTimeFormat)
                        .stream()
                        .map(viewStats -> new ViewStats(viewStats.getApp(), viewStats.getUri(), viewStats.getHits()))
                        .collect(Collectors.toList());
            }
        } else {
            if (Boolean.TRUE.equals(unique)) {
                return statRepository.findUniqueStats(startTimeFormat, endTimeFormat, uris).stream()
                        .map(viewStats -> new ViewStats(viewStats.getApp(), viewStats.getUri(), viewStats.getHits()))
                        .collect(Collectors.toList());
            } else {
                return statRepository.findStats(startTimeFormat, endTimeFormat, uris).stream()
                        .map(viewStats -> new ViewStats(viewStats.getApp(), viewStats.getUri(), viewStats.getHits()))
                        .collect(Collectors.toList());
            }
        }
    }
}
