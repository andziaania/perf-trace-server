package com.pawelczyk.perftraceserver.repository;

import com.pawelczyk.perftraceserver.model.WebappDaily;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ania.pawelczyk
 * @since 08.09.2019.
 */
public interface WebappDailyRepository extends JpaRepository<WebappDaily, Long> {
}
