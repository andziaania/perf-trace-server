package com.pawelczyk.perftraceserver.repository;

import com.pawelczyk.perftraceserver.model.WebappPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author ania.pawelczyk
 * @since 04.10.2019.
 */
public interface WebappPathRepository extends JpaRepository<WebappPath, Long> {

  Optional<WebappPath> findByPath(String path);

}
