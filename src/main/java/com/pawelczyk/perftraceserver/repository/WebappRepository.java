package com.pawelczyk.perftraceserver.repository;

import com.pawelczyk.perftraceserver.model.Webapp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author ania.pawelczyk
 * @since 06.09.2019.
 */
public interface WebappRepository extends JpaRepository<Webapp, Long> {
  Optional<Webapp> findByUrl(String url);
}