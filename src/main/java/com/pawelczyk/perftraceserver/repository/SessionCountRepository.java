package com.pawelczyk.perftraceserver.repository;

import com.pawelczyk.perftraceserver.model.SessionCount;
import com.pawelczyk.perftraceserver.model.Webapp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author ania.pawelczyk
 * @since 18.09.2019.
 */
public interface SessionCountRepository extends JpaRepository<SessionCount, Long> {
  Optional<SessionCount> findByWebapp(Webapp webapp);
}
