package com.pawelczyk.perftraceserver.repository;

import com.pawelczyk.perftraceserver.model.Session;
import com.pawelczyk.perftraceserver.model.Webapp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author ania.pawelczyk
 * @since 18.09.2019.
 */
public interface SessionRepository extends JpaRepository<Session, Long> {
  Optional<Session> findByWebapp(Webapp webapp);
}
