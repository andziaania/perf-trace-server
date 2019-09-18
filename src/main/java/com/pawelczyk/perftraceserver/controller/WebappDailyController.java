package com.pawelczyk.perftraceserver.controller;

import com.pawelczyk.perftraceserver.model.WebappDaily;
import com.pawelczyk.perftraceserver.repository.WebappDailyRepository;
import com.pawelczyk.perftraceserver.utils.SystemDefaultTimeDateUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ania.pawelczyk
 * @since 08.09.2019.
 */
@RestController
@CrossOrigin("http://localhost:4200") //4200
@RequestMapping("/api/users")
public class WebappDailyController {

  private WebappDailyRepository webappDailyRepository;

  private SystemDefaultTimeDateUtil systemDefaultTimeDateUtil;

  WebappDailyController(WebappDailyRepository webappDailyRepository, SystemDefaultTimeDateUtil systemDefaultTimeDateUtil) {
    this.webappDailyRepository = webappDailyRepository;
    this.systemDefaultTimeDateUtil = systemDefaultTimeDateUtil;
  }

  @GetMapping("/total/day/{timestamp}")
  public List<Long> getTotalWabappUsersHourly(@PathVariable Long timestamp) {
    final Optional<WebappDaily> webappDailyOpt = webappDailyRepository.findByTimestamp(timestamp);
    if (!webappDailyOpt.isPresent()) {
      return Collections.nCopies(24, 0L);
    }
    return webappDailyOpt.get().getUsersNumberHourly();
  }

  @GetMapping("/total/week/{timestamp}")
  public List<Long> getTotalWabappUsersWeekly(@PathVariable Long timestamp) {
    final Long timestampWeekStart = systemDefaultTimeDateUtil.getWeekStartTimestamp(timestamp);
    final Long timestampWeekEnd = systemDefaultTimeDateUtil.getWeekEndTimestamp(timestamp);
    final List<WebappDaily> webappDailyPeriod = webappDailyRepository.findByTimestampBetweenOrderByTimestampAsc(timestampWeekStart, timestampWeekEnd);

    return getEveryDayUsersCount(webappDailyPeriod, timestampWeekStart, timestampWeekEnd);
  }

  @GetMapping("/total/month/{timestamp}")
  public List<Long> getTotalWabappUsersMonthly(@PathVariable Long timestamp) {
    final Long timestampMonthStart = systemDefaultTimeDateUtil.getMonthStartTimestamp(timestamp);
    final Long timestampMonthEnd = systemDefaultTimeDateUtil.getMonthEndTimestamp(timestamp);
    final List<WebappDaily> webappDailyPeriod = webappDailyRepository.findByTimestampBetweenOrderByTimestampAsc(timestampMonthStart, timestampMonthEnd);

    return getEveryDayUsersCount(webappDailyPeriod, timestampMonthStart, timestampMonthEnd);
  }

  private List<Long> getEveryDayUsersCount(List<WebappDaily> webappDailyPeriod, Long startTimestamp, Long endTimestamp) {
    final LocalDate startDate = systemDefaultTimeDateUtil.getDate(startTimestamp);
    final LocalDate endDate = systemDefaultTimeDateUtil.getDate(endTimestamp);
    final int ONE_DAY = 1;
    final Long NO_USERS = 0L;

    final List<Long> usersEveryDayCount = new ArrayList<>();

    Iterator<WebappDaily> iterator = webappDailyPeriod.iterator();
    WebappDaily webappDaily = iterator.hasNext() ?  iterator.next() : new WebappDaily();

    for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(ONE_DAY)) {
      if (date.isEqual(systemDefaultTimeDateUtil.getDate(webappDaily.getTimestamp()))) {
        usersEveryDayCount.add(webappDaily.getUsersNumber());
        webappDaily = iterator.hasNext() ?  iterator.next() : webappDaily;
      } else {
        usersEveryDayCount.add(NO_USERS);
      }
    }

    return usersEveryDayCount;
  }
}
