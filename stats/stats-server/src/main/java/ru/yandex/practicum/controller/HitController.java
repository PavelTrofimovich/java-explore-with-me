package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.EndpointHitDto;
import ru.yandex.practicum.ViewStats;
import ru.yandex.practicum.service.HitService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class HitController {
    private final HitService hitService;

    @PostMapping("/hit")
    public void addHit(@Valid EndpointHitDto endpointHitDto) {
        hitService.saveHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        return hitService.getStatistics(start, end, uris, unique);
    }
}
