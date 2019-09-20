package com.pawelczyk.perftraceserver.dbscheduler;

import com.pawelczyk.perftraceserver.model.Session;
import com.pawelczyk.perftraceserver.model.WebappDaily;
import com.pawelczyk.perftraceserver.repository.SessionRepository;
import com.pawelczyk.perftraceserver.repository.WebappDailyRepository;
import com.pawelczyk.perftraceserver.utils.SystemDefaultTimeDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ania.pawelczyk
 * @since 19.09.2019.
 */
@Configuration
@EnableScheduling
public class SessionsMigrationScheduler {

  private Logger log = LoggerFactory.getLogger(SessionsMigrationScheduler.class);

  private SessionRepository sessionRepository;

  private WebappDailyRepository webappDailyRepository;

  private SystemDefaultTimeDateUtil systemDefaultTimeDateUtil;

  SessionsMigrationScheduler(SessionRepository sessionRepository, WebappDailyRepository webappDailyRepository, SystemDefaultTimeDateUtil systemDefaultTimeDateUtil) {
    this.sessionRepository = sessionRepository;
    this.webappDailyRepository = webappDailyRepository;
    this.systemDefaultTimeDateUtil = systemDefaultTimeDateUtil;
  }

  private final static int MINUTE = 1000 * 60;

  // The duration between the end of last execution and the start of next execution is fixed.
  // The task always waits until the previous one is finished.
  @Scheduled(fixedDelay = MINUTE * 1)
  public void run() {
    log.info("Scheduler started");
    List<Session> allSessions = getAndDeleteAllDbSessions();

    Map<LocalDate, List<Session>> dateSessionMap = mapSessionsByDate(allSessions);

    dateSessionMap.forEach((date, sessionList) -> {

      WebappDaily webappDaily = getWebappDailyFromDBOrCreate(date);

      ArrayList<Long> usersNumberHourly = (ArrayList<Long>)(webappDaily.getUsersNumberHourly());
      Long returningUsersNumber = 0L;
      for (Session session : sessionList) {
        // increment usersNumberHourly at the hour index
        int hour = session.getTimestamp().getHour();
        usersNumberHourly.set(hour, usersNumberHourly.get(hour) + 1L);
        // if user is of the returning type, increment counter
        returningUsersNumber += session.getIsReturning() ? 1L : 0L;
      }
      // Required as there is new value needed for webappDaily.usersNumber. The webappDaily.usersNumberHourly updates
      // by reference to the object.
      webappDaily.setUsersNumber(usersNumberHourly);
      webappDaily.setReturningUsersNumber(returningUsersNumber);

      webappDailyRepository.save(webappDaily);
      log.info("Scheduler saved % %", webappDaily.getTimestamp(), webappDaily.getUsersNumber());
    });

  }

  @Transactional
  private List<Session> getAndDeleteAllDbSessions() {
    List<Session> allSessions = sessionRepository.findAll();
    sessionRepository.deleteAll();
    log.info("Scheduler deleted all sessions");
    return allSessions;
  }

  private Map<LocalDate, List<Session>> mapSessionsByDate(List<Session> allSessions) {
    Map<LocalDate, List<Session>> dateSessionMap = new HashMap<>();
    allSessions.forEach(session -> {
      LocalDate date = session.getTimestamp().toLocalDate();
      if (!dateSessionMap.containsKey(date)) {
        dateSessionMap.put(date, new ArrayList<>());
      }
      dateSessionMap.get(date).add(session);
    });
    return dateSessionMap;
  }

  private WebappDaily getWebappDailyFromDBOrCreate(LocalDate date) {
    Long startOfDayTimestamp = systemDefaultTimeDateUtil.getStartOfDayTimestamp(date);
    Optional<WebappDaily> webappDailyOpt = webappDailyRepository.findByTimestamp(startOfDayTimestamp);
    return webappDailyOpt.orElseGet(() -> new WebappDaily(startOfDayTimestamp, createHoursEmptyList()));
  }

  private List<Long> createHoursEmptyList() {
    return Arrays.stream(new long[24]).boxed().collect(Collectors.toList());
  }

}
