package com.pawelczyk.perftraceserver.model;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ania.pawelczyk
 * @since 09.09.2019.
 */
public class WebappDailyTest {

  private final Long anyTimestamp = new Timestamp(System.currentTimeMillis()).getTime();

  @Test
  public void constructor_setUsersNumber() {
    List<Long> usersNumberHourly = Arrays.asList(2L, 44L, 15L);
    Long usersNumber = 2L + 44 + 15;

    WebappDaily webappDaily = new WebappDaily(anyTimestamp, usersNumberHourly);
    assertEquals(webappDaily.getUsersNumber(), usersNumber);
  }

  @Test
  public void constructor_whenEmptyUsersNumberListPassed_usersNumberisZero() {
    List<Long> usersNumberHourly = Collections.emptyList();
    Long noUsers = 0L;

    WebappDaily webappDaily = new WebappDaily(anyTimestamp, usersNumberHourly);
    assertEquals(webappDaily.getUsersNumber(), noUsers);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_whenNullUsersNumberListPassed_throwsException() {
    new WebappDaily(anyTimestamp, null);
  }

  @Test()
  public void constructor_assureThereIsTheDefaultConstructor() {
    new WebappDaily();
  }
}