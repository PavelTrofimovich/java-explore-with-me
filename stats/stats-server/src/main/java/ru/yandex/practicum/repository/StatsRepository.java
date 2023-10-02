package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yandex.practicum.ViewStats;
import ru.yandex.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {
    @Query("SELECT NEW ru.yandex.practicum.ViewStats(H.app, H.uri, COUNT (H.ip) AS hits) " +
            "FROM ru.yandex.practicum.model.EndpointHit AS H " +
            "WHERE (H.timestamp BETWEEN :start AND :end) AND H.uri IN (:uris) " +
            "GROUP BY H.app, H.uri ORDER BY hits DESC")
    List<ViewStats> findStats(@Param("start") LocalDateTime start,
                              @Param("end") LocalDateTime end,
                              @Param("uris") List<String> uris
    );

    @Query("SELECT NEW ru.yandex.practicum.ViewStats(H.app, H.uri, COUNT (distinct H.ip) AS hits) " +
            "FROM ru.yandex.practicum.model.EndpointHit AS H " +
            "WHERE (H.timestamp BETWEEN :start AND :end) AND H.uri IN (:uris) " +
            "GROUP BY H.app, H.uri ORDER BY hits DESC")
    List<ViewStats> findUniqueStats(@Param("start") LocalDateTime start,
                                    @Param("end") LocalDateTime end,
                                    @Param("uris") List<String> uris
    );

    @Query("SELECT NEW ru.yandex.practicum.ViewStats(H.app, H.uri, COUNT (H.ip) AS hits) " +
            "FROM ru.yandex.practicum.model.EndpointHit AS H " +
            "WHERE (H.timestamp BETWEEN :start AND :end) " +
            "GROUP BY H.app, H.uri ORDER BY hits DESC")
    List<ViewStats> findStatsForAll(@Param("start") LocalDateTime start,
                                    @Param("end") LocalDateTime end
    );

    @Query("SELECT NEW ru.yandex.practicum.ViewStats(H.app, H.uri, COUNT (distinct H.ip) AS hits) " +
            "FROM ru.yandex.practicum.model.EndpointHit AS H " +
            "WHERE (H.timestamp BETWEEN :start AND :end) " +
            "GROUP BY H.app, H.uri ORDER BY hits DESC")
    List<ViewStats> findUniqueStatsForAll(@Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end
    );
}
