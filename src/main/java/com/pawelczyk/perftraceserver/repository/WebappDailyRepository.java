package com.pawelczyk.perftraceserver.repository;

import com.pawelczyk.perftraceserver.model.WebappDaily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author ania.pawelczyk
 * @since 08.09.2019.
 */
public interface WebappDailyRepository extends JpaRepository<WebappDaily, Long> {

  Optional<WebappDaily> findByDate(LocalDate date);

  List<WebappDaily> findByDateBetweenOrderByDateAsc(LocalDate dateStart, LocalDate dateEnd);
}
