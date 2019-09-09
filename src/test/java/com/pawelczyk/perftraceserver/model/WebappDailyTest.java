package com.pawelczyk.perftraceserver.model;

import org.junit.Test;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ania.pawelczyk
 * @since 09.09.2019.
 */
public class WebappDailyTest {

  @Test
  public void constructor_setUsersNumber() {
    List<Long> usersNumberHourly = Arrays.asList(2L, 44L, 15L);
    long usersNumber = 2 + 44 + 15;

    WebappDaily webappDaily = new WebappDaily(LocalDate.now(), usersNumberHourly);
    assertEquals(webappDaily.getUsersNumber(), usersNumber);
  }

  @Test
  public void constructor_whenEmptyUsersNumberListPassed_usersNumberisZero() {
    List<Long> usersNumberHourly = Collections.emptyList();

    WebappDaily webappDaily = new WebappDaily(LocalDate.now(), usersNumberHourly);
    assertEquals(webappDaily.getUsersNumber(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_whenNullUsersNumberListPassed_throwsException() {
    new WebappDaily(LocalDate.now(), null);
  }
}