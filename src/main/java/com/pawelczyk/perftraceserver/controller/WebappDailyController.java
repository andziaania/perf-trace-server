package com.pawelczyk.perftraceserver.controller;

import com.pawelczyk.perftraceserver.model.WebappDaily;
import com.pawelczyk.perftraceserver.repository.WebappDailyRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author ania.pawelczyk
 * @since 08.09.2019.
 */
@CrossOrigin("http://localhost:4200")
@RestController
public class WebappDailyController {

  private WebappDailyRepository webappDailyRepository;

  WebappDailyController(WebappDailyRepository webappDailyRepository) {
    this.webappDailyRepository = webappDailyRepository;
  }

  @GetMapping("/api/users/total/day/{timestamp}")
  public List<Long> getTotalWabappUsersHourly(@PathVariable("timestamp") Long timestamp) {
    final Optional<WebappDaily> webappDailyOpt = webappDailyRepository.findByTimestamp(timestamp);
    if (!webappDailyOpt.isPresent()) {
      return Collections.nCopies(24, 0L);
    }
    return webappDailyOpt.get().getUsersNumberHourly();
  }
}
