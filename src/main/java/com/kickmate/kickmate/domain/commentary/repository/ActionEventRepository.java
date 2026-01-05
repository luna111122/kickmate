package com.kickmate.kickmate.domain.commentary.repository;

import com.kickmate.kickmate.domain.commentary.entity.RawActionEvent;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionEventRepository extends JpaRepository<RawActionEvent, Long> {

    List<RawActionEvent> findByGameIdAndActionIdGreaterThanEqualOrderByActionIdAsc(
            Long gameId,
            Integer actionId,
            Pageable pageable
    );

}
