package com.kickmate.kickmate.domain.commentary.repository;

import com.kickmate.kickmate.domain.commentary.entity.RawMatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatchInfoRepository extends JpaRepository<RawMatchInfo, Long> {


    Optional<RawMatchInfo> findByGameId(Long gameId);

    @Query("""
        SELECT r
        FROM RawMatchInfo r
        WHERE r.gameDay = :round
          AND FUNCTION('MONTH', r.gameDate) = :month
        ORDER BY r.gameDate ASC
    """)
    List<RawMatchInfo> findAllByMonthAndRound(
            @Param("month") Integer month,
            @Param("round") Integer round
    );

}
