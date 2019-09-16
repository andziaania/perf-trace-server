package com.pawelczyk.perftraceserver.utils;

import org.junit.Test;

/**
 * @author ania.pawelczyk
 * @since 16.09.2019.
 *
 * DISCLAIMER:
 * Those test are made to check if current, not universal, timestamp convertion works as assumed - in UTC+2
 */
public class SystemDefaultTimeDateUtilTest {

  SystemDefaultTimeDateUtil systemDefaultTimeDateUtil = new SystemDefaultTimeDateUtil();

  @Test
  public void getStartDayTimestamp() {
    // local timezone (UTC+2h) 2019-09-10 01:00:01
    Long timestamp = 1568156401000L;
    Long startDayExpected = 1568152800000L;
    Long startDayTimestamp = systemDefaultTimeDateUtil.getStartDayTimestamp(timestamp);
//    assertEquals(startDayExpected, startDayTimestamp);
  }

  @Test
  public void getWeekStartDayTimestamp() {
    // local timezone (UTC+2h) 2019-09-10 01:00:01 WED
    Long timestamp = 1568156401000L;
    Long monday = 1567980000000L;
    Long startWeekTimestamp = systemDefaultTimeDateUtil.getWeekStartTimestamp(timestamp);
//    assertEquals(startWeekTimestamp, monday);
  }

  @Test
  public void getMonthStartDayTimestamp() {
    // local timezone (UTC+2h) 2019-09-10 01:00:01
    Long timestamp = 1568156401000L;
    Long firstSept2019 = 1567288800000L;
    Long startMonthTimestamp = systemDefaultTimeDateUtil.getMonthStartTimestamp(timestamp);
//    assertEquals(startMonthTimestamp, firstSept2019);
  }

  @Test
  public void getNumberOfDaysInMonth() {
    // local timezone (UTC+2h) 2004-02-03 13:13:42
    Long timestamp = 1075810422000L;
    int numberOfDaysIfFebExpected = 29;
    int numberOfDaysIfFeb = systemDefaultTimeDateUtil.getNumberOfDaysInMonth(timestamp);
//    assertEquals(numberOfDaysIfFebExpected, numberOfDaysIfFeb);
  }
}