package com.pawelczyk.perftraceserver.repository;

import com.pawelczyk.perftraceserver.model.WebappLoadingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * @author ania.pawelczyk
 * @since 24.09.2019.
 */
public interface WebappLoadingTimeRepository extends JpaRepository<WebappLoadingTime, Long> {

  @Query(value = "SELECT MAX(w.loadingTime), MIN(w.loadingTime), AVG(w.loadingTime) FROM WebappLoadingTime w")
  Object findMaxLoadingTime();
}
