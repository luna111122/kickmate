package com.kickmate.kickmate.domain.commentary.repository;

import com.kickmate.kickmate.domain.commentary.entity.RawActionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionEventRepository extends JpaRepository<RawActionEvent, Long> {


}
