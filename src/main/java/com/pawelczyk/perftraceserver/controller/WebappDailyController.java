package com.pawelczyk.perftraceserver.controller;

import com.pawelczyk.perftraceserver.model.WebappDaily;
import com.pawelczyk.perftraceserver.repository.WebappDailyRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

  @GetMapping("/api/perf-trace/users/total/day/{timestamp}")
  public List<WebappDaily> getTotalWabappUsersHourly(@PathVariable("timestamp") long timestamp) {
    return webappDailyRepository.findAll();
  }
}
