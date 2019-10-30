package com.pawelczyk.perftraceserver.controller.getter;

import com.pawelczyk.perftraceserver.model.WebappDaily;
import com.pawelczyk.perftraceserver.repository.WebappDailyRepository;
import com.pawelczyk.perftraceserver.service.WebappDailyService;
import com.pawelczyk.perftraceserver.utils.TimeDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author ania.pawelczyk
 * @since 08.09.2019.
 */
@RestController
@RequestMapping("/api/users")
public class WebappDailyController {

  Logger log = LoggerFactory.getLogger(WebappDailyController.class);

  private WebappDailyRepository webappDailyRepository;

  private WebappDailyService webappDailyService;

  private TimeDateUtil timeDateUtil;

  private Function<WebappDaily, Long> getUsersNumberFormWebappDailyFun = WebappDaily::getUsersNumber;

  private Function<WebappDaily, Long> getReturningUsersNumberFormWebappDailyFun = WebappDaily::getReturningUsersNumber;


  WebappDailyController(WebappDailyRepository webappDailyRepository, WebappDailyService webappDailyService, TimeDateUtil timeDateUtil) {
    this.webappDailyRepository = webappDailyRepository;
    this.webappDailyService = webappDailyService;
    this.timeDateUtil = timeDateUtil;
  }

  /***** Total users count methods ****/
  @GetMapping("/total/day")
  public List<Long> getTotalWabappUsersHourly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
    final Optional<WebappDaily> webappDailyOpt = webappDailyRepository.findByDate(date);
    if (!webappDailyOpt.isPresent()) {
      return webappDailyService.createEmptyHoursList();
    }
    return webappDailyOpt.get().getUsersNumberHourly();
  }

  @GetMapping("/total/week")
  public List<Long> getTotalWabappUsersWeekly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
    final LocalDate weekStartDate = timeDateUtil.getWeekStartDate(date);
    final LocalDate weekEndDate = timeDateUtil.getWeekEndDate(date);
    final List<WebappDaily> webappDailyPeriod = webappDailyRepository.findByDateBetweenOrderByDateAsc(weekStartDate, weekEndDate);

    return webappDailyService.getEveryDayUsersCount(webappDailyPeriod, weekStartDate, weekEndDate, getUsersNumberFormWebappDailyFun);
  }

  @GetMapping("/total/month")
  public List<Long> getTotalWabappUsersMonthly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
    final LocalDate monthStartDate = timeDateUtil.getMonthStartDate(date);
    final LocalDate monthEndDate = timeDateUtil.getMonthEndDate(date);
    final List<WebappDaily> webappDailyPeriod = webappDailyRepository.findByDateBetweenOrderByDateAsc(monthStartDate, monthEndDate);

    return webappDailyService.getEveryDayUsersCount(webappDailyPeriod, monthStartDate, monthEndDate, getUsersNumberFormWebappDailyFun);
  }

  /***** Returning users count methods ****/
  @GetMapping("/returning/day")
  public List<Long> getReturningWabappUsersHourly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
    final Optional<WebappDaily> webappDailyOpt = webappDailyRepository.findByDate(date);
    if (!webappDailyOpt.isPresent()) {
      return webappDailyService.createEmptyHoursList();
    }
    return webappDailyOpt.get().getReturningUsersNumberHourly();
  }

  @GetMapping("/returning/week")
  public List<Long> getReturningWabappUsersWeekly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
    final LocalDate weekStartDate = timeDateUtil.getWeekStartDate(date);
    final LocalDate weekEndDate = timeDateUtil.getWeekEndDate(date);
    final List<WebappDaily> webappDailyPeriod = webappDailyRepository.findByDateBetweenOrderByDateAsc(weekStartDate, weekEndDate);

    return webappDailyService.getEveryDayUsersCount(webappDailyPeriod, weekStartDate, weekEndDate, getReturningUsersNumberFormWebappDailyFun);
  }

  @GetMapping("/returning/month")
  public List<Long> getReturningWabappUsersMonthly(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
    final LocalDate monthStartDate = timeDateUtil.getMonthStartDate(date);
    final LocalDate monthEndDate = timeDateUtil.getMonthEndDate(date);
    final List<WebappDaily> webappDailyPeriod = webappDailyRepository.findByDateBetweenOrderByDateAsc(monthStartDate, monthEndDate);

    return webappDailyService.getEveryDayUsersCount(webappDailyPeriod, monthStartDate, monthEndDate, getReturningUsersNumberFormWebappDailyFun);
  }
}
