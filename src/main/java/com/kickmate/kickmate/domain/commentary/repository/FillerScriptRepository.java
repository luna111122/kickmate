package com.kickmate.kickmate.domain.commentary.repository;

import com.kickmate.kickmate.domain.commentary.entity.RawFillerScript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FillerScriptRepository extends JpaRepository<RawFillerScript, Long> {


    Optional<RawFillerScript> findFirstByGameId(Long gameId);


}
