package ru.yandex.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {
    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public void saveStatistics(EndpointHitDto endpointHitDto) {
        post(endpointHitDto);
    }

    public ResponseEntity<Object> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris,
                                                boolean unique) {
        String strOfUris = String.join(",", uris);

        Map<String, Object> parameters = Map.of("start", start, "end", end, "uris", strOfUris, "unique",
                unique);

        return get(parameters);
    }
}