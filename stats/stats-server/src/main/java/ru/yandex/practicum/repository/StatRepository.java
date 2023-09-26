package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.ViewStats;
import ru.yandex.practicum.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatRepository extends JpaRepository<EndpointHit, Integer> {
    @Query("SELECT H.app, H.uri, COUNT (H.ip) AS hits " +
            "FROM EndpointHit AS H " +
            "WHERE (H.timestamp BETWEEN :start AND :end) AND H.uri IN (:uris) " +
            "GROUP BY H.app, H.uri ORDER BY hits DESC")
    List<ViewStats> findStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT H.app, H.uri, COUNT (distinct H.ip) AS hits " +
            "FROM EndpointHit AS H " +
            "WHERE (H.timestamp BETWEEN :start AND :end) AND H.uri IN (:uris) " +
            "GROUP BY H.app, H.uri ORDER BY hits DESC")
    List<ViewStats> findUniqueStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT H.app, H.uri, COUNT (H.id) AS hits " +
            "FROM EndpointHit AS H " +
            "WHERE (H.timestamp BETWEEN :start AND :end) " +
            "GROUP BY H.app, H.uri ORDER BY hits DESC")
    List<ViewStats> findStatsForAll(LocalDateTime start, LocalDateTime end);

    @Query("SELECT H.app, H.uri, COUNT (distinct H.ip) AS hits " +
            "FROM EndpointHit AS H " +
            "WHERE (H.timestamp BETWEEN :start AND :end) " +
            "GROUP BY H.app, H.uri ORDER BY hits DESC")
    List<ViewStats> findUniqueStatsForAll(LocalDateTime start, LocalDateTime end);
}
