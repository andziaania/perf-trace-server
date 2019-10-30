package com.pawelczyk.perftraceserver.service;

import com.pawelczyk.perftraceserver.model.WebappDaily;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * @author ania.pawelczyk
 * @since 30.10.2019.
 */
@Service
public class WebappDailyService {
  public List<Long> getEveryDayUsersCount(List<WebappDaily> webappDailyPeriod, LocalDate startDate, LocalDate endDate, Function<WebappDaily, Long> countFunction) {
    final int ONE_DAY = 1;
    final Long NO_USERS = 0L;

    final List<Long> usersEveryDayCount = new ArrayList<>();

    Iterator<WebappDaily> iterator = webappDailyPeriod.iterator();
    WebappDaily webappDaily = iterator.hasNext() ?  iterator.next() : null;

    for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(ONE_DAY)) {
      if (webappDaily != null && date.isEqual(webappDaily.getDate())) {
        Long count = countFunction.apply(webappDaily);
        usersEveryDayCount.add(count);
        webappDaily = iterator.hasNext() ?  iterator.next() : webappDaily;
      } else {
        usersEveryDayCount.add(NO_USERS);
      }
    }

    return usersEveryDayCount;
  }

  public List<Long> createEmptyHoursList() {
    return Collections.nCopies(24, 0L);
  }
}
