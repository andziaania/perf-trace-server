package com.pawelczyk.perftraceserver.utils;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * @author ania.pawelczyk
 * @since 16.09.2019.
 */
@Component
public class TimeDateUtil {

  public LocalDate getWeekStartDate(LocalDate date) {
    return date.with(DayOfWeek.MONDAY);
  }

  public LocalDate getWeekEndDate(LocalDate date) {
    return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
  }

  public LocalDate getMonthStartDate(LocalDate date) {
    return date.withDayOfMonth(1);
  }

  public LocalDate getMonthEndDate(LocalDate date) {
    return date.with(TemporalAdjusters.lastDayOfMonth());
  }

}
