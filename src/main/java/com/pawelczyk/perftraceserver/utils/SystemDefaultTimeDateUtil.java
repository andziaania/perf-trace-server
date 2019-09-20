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
 *
 * DISCLAIMER:
 * This class solves the problem of differend time zones - assuming that the client is in the same time zone as server.
 * In the future differences between the client and server time zones are to be implemented.
 *
 * Basing on:
 * https://stackoverflow.com/questions/51952984/how-can-i-convert-a-time-in-milliseconds-to-zoneddatetime/51953287
 * https://stackoverflow.com/questions/32437550/whats-the-difference-between-instant-and-localdatetime/
 * https://stackoverflow.com/questions/10308356/how-to-obtain-the-start-time-and-end-time-of-a-day
 * https://stackoverflow.com/questions/23215299/how-to-convert-a-localdate-to-an-instant/23886207
 * https://stackoverflow.com/questions/55645505/how-to-convert-zoneddatetime-to-millisecond-in-java
 * https://www.w3resource.com/java-exercises/datetime/java-datetime-exercise-33.php
 */
@Component
public class SystemDefaultTimeDateUtil {

  public Long getStartDayTimestamp(Long timestamp) {
    LocalDate date = getDate(timestamp);
    return getStartOfDayTimestamp(date);
  }

  public Long getWeekStartTimestamp(Long timestamp) {
    LocalDate date = getDate(timestamp);
    LocalDate monday = date.with(DayOfWeek.MONDAY);
    return getStartOfDayTimestamp(monday);
  }

  public Long getWeekEndTimestamp(Long timestamp) {
    LocalDate date = getDate(timestamp);
    LocalDate sunday = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    return getStartOfDayTimestamp(sunday);
  }

  public Long getMonthStartTimestamp(Long timestamp) {
    LocalDate date = getDate(timestamp);
    LocalDate monday = date.withDayOfMonth(1);
    return getStartOfDayTimestamp(monday);
  }

  public Long getMonthEndTimestamp(Long timestamp) {
    LocalDate date = getDate(timestamp);
    LocalDate lastMonthDay = date.with(TemporalAdjusters.lastDayOfMonth());
    return getStartOfDayTimestamp(lastMonthDay);
  }

  public int getNumberOfDaysInMonth(Long timestamp) {
    LocalDate date = getDate(timestamp);
    return date.lengthOfMonth();
  }

  public LocalDate getDate(Long timestamp) {
    ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    return zdt.toLocalDate();
  }

  public Long getStartOfDayTimestamp(LocalDate date) {
    return  date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

}
