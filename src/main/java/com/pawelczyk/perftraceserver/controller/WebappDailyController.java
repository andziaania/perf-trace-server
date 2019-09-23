package com.pawelczyk.perftraceserver.controller;

import com.pawelczyk.perftraceserver.model.WebappDaily;
import com.pawelczyk.perftraceserver.repository.WebappDailyRepository;
import com.pawelczyk.perftraceserver.utils.TimeDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/users")
public class WebappDailyController {

  Logger log = LoggerFactory.getLogger(WebappDailyController.class);

  private WebappDailyRepository webappDailyRepository;

  private TimeDateUtil timeDateUtil;

  WebappDailyController(WebappDailyRepository webappDailyRepository, TimeDateUtil timeDateUtil) {
    this.webappDailyRepository = webappDailyRepository;
    this.timeDateUtil = timeDateUtil;
  }

  @GetMapping("/total/day")
  public List<Long> getTotalWabappUsersHourly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
    final Optional<WebappDaily> webappDailyOpt = webappDailyRepository.findByDate(date);
    if (!webappDailyOpt.isPresent()) {
      return Collections.nCopies(24, 0L);
    }
    return webappDailyOpt.get().getUsersNumberHourly();
  }

  @GetMapping("/total/week")
  public List<Long> getTotalWabappUsersWeekly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
    final LocalDate weekStartDate = timeDateUtil.getWeekStartDate(date);
    final LocalDate weekEndDate = timeDateUtil.getWeekEndDate(date);
    final List<WebappDaily> webappDailyPeriod = webappDailyRepository.findByDateBetweenOrderByDateAsc(weekStartDate, weekEndDate);

    return getEveryDayUsersCount(webappDailyPeriod, weekStartDate, weekEndDate);
  }

  @GetMapping("/total/month")
  public List<Long> getTotalWabappUsersMonthly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
    final LocalDate monthStartDate = timeDateUtil.getMonthStartDate(date);
    final LocalDate monthEndDate = timeDateUtil.getMonthEndDate(date);
    final List<WebappDaily> webappDailyPeriod = webappDailyRepository.findByDateBetweenOrderByDateAsc(monthStartDate, monthEndDate);

    return getEveryDayUsersCount(webappDailyPeriod, monthStartDate, monthEndDate);
  }

  private List<Long> getEveryDayUsersCount(List<WebappDaily> webappDailyPeriod, LocalDate startDate, LocalDate endDate) {
    final int ONE_DAY = 1;
    final Long NO_USERS = 0L;

    final List<Long> usersEveryDayCount = new ArrayList<>();

    Iterator<WebappDaily> iterator = webappDailyPeriod.iterator();
    WebappDaily webappDaily = iterator.hasNext() ?  iterator.next() : new WebappDaily();

    for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(ONE_DAY)) {
      if (date.isEqual(webappDaily.getDate())) {
        usersEveryDayCount.add(webappDaily.getUsersNumber());
        webappDaily = iterator.hasNext() ?  iterator.next() : webappDaily;
      } else {
        usersEveryDayCount.add(NO_USERS);
      }
    }

    return usersEveryDayCount;
  }
}
