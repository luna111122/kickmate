package com.kickmate.kickmate.domain.commentary.repository;

import com.kickmate.kickmate.domain.commentary.entity.RawMatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchInfoRepository extends JpaRepository<RawMatchInfo, Long> {


    Optional<RawMatchInfo> findByGameId(Long gameId);

    @Query("""
        SELECT r
        FROM RawMatchInfo r
        WHERE r.gameDay = :round
          AND r.gameDate >= :startDate
          AND r.gameDate < :endDate
        ORDER BY r.gameDate ASC
    """)
    List<RawMatchInfo> findAllByRoundAndGameDateBetween(
            @Param("round") Integer round,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
