package com.pawelczyk.perftraceserver.repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.pawelczyk.perftraceserver.model.WebappDaily;

/**
 * @author ania.pawelczyk
 * @since 08.09.2019.
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class WebappDailyRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private WebappDailyRepository webappDailyRepository;

  @Test
  public void persist() {
    LocalDate today = LocalDate.now();
    List<Long> hours = Arrays.asList(1L,2L,3L,4L,5L,6L,7L,8L,8L,10L,1L,2L,3L,4L,5L,6L,7L,8L,8L,20L,21L,22L,23L,24L);

    WebappDaily webappDaily = new WebappDaily(today, hours);
    entityManager.persist(webappDaily);

    WebappDaily webappDailyFromDB = webappDailyRepository.findAll().get(0);

    List<Long> hoursFromDB = webappDailyFromDB.getUsersNumberHourly();
    assertTrue(IntStream.range(0, webappDaily.getUsersNumberHourly().size())
            .allMatch(i -> hours.get(i).equals(hoursFromDB.get(i))));
  }

  @Test(expected = IllegalArgumentException.class)
  public void persist_whenHoursListIsNull_IllegalArgumentExceptio() {
    WebappDaily webappDaily = new WebappDaily(LocalDate.now(), null);
    entityManager.persist(webappDaily);
  }
}