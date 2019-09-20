package com.pawelczyk.perftraceserver.mockdbdata;

import com.pawelczyk.perftraceserver.model.WebappDaily;
import com.pawelczyk.perftraceserver.repository.WebappDailyRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author ania.pawelczyk
 * @since 08.09.2019.
 */
@Component
public class WebappdailyDataGenerator {

  private final long MAX_USERS_NUMBER = 10;
  private final long DAYS = 100;

  private WebappDailyRepository webappDailyRepository;

  WebappdailyDataGenerator(WebappDailyRepository webappDailyRepository) {
    this.webappDailyRepository = webappDailyRepository;
  }

  /**
   * Generate DB WebappDaily rows for the past long DAYS days.
   */
  @PostConstruct
  public void genetate() {
    LocalDate twoDaysAgo = LocalDate.now().minusDays(2);

    for (int i = 0; i < DAYS; i++) {

      LocalDate date = twoDaysAgo.minusDays(i);

      List<Long> hours =  Arrays.stream(new Long[24])
              .map(zero -> (long) (Math.random() * MAX_USERS_NUMBER))
              .collect(Collectors.toList());
      WebappDaily webappDaily = new WebappDaily(date, hours);
      webappDailyRepository.save(webappDaily);
    }
  }

}
