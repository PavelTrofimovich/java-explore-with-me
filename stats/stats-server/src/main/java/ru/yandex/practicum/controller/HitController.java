package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.ViewStats;
import ru.yandex.practicum.model.EndpointHit;
import ru.yandex.practicum.service.HitService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HitController {
    private final HitService hitService;

    @PostMapping("/hit")
    public EndpointHit addHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        log.info("Сведения о запросе были сохранены.");
        return hitService.saveHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Статистика для uri {} была выгружена", uris);
        return hitService.getStatistics(start, end, uris, unique);
    }
}
