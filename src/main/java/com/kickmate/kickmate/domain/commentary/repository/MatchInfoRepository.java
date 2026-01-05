package com.kickmate.kickmate.domain.commentary.repository;

import com.kickmate.kickmate.domain.commentary.entity.RawMatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchInfoRepository extends JpaRepository<RawMatchInfo, Long> {

    Optional<RawMatchInfo> findByGameId(Long gameId);

}
