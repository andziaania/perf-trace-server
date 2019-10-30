package com.pawelczyk.perftraceserver.controller.recorder;

import com.pawelczyk.perftraceserver.model.Session;
import com.pawelczyk.perftraceserver.model.Webapp;
import com.pawelczyk.perftraceserver.model.WebappLoadingTime;
import com.pawelczyk.perftraceserver.model.WebappPath;
import com.pawelczyk.perftraceserver.repository.SessionRepository;
import com.pawelczyk.perftraceserver.repository.WebappLoadingTimeRepository;
import com.pawelczyk.perftraceserver.repository.WebappPathRepository;
import com.pawelczyk.perftraceserver.repository.WebappRepository;
import com.pawelczyk.perftraceserver.service.RecorderService;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @author ania.pawelczyk
 * @since 17.09.2019.
 */
@RestController
@RequestMapping("/api/recorder")
public class RecorderController {

  private static final String USER_COOKIE_NAME = "pt_userid";

  private static final String SESSION_COOKIE_NAME = "pt_session";

  private final int SESSION_COOKIE_MAX_AGE_SEC = 60 * 3;

  private Logger log = LoggerFactory.getLogger(RecorderController.class);

  private SessionRepository sessionRepository;

  private WebappRepository webappRepository;

  private WebappLoadingTimeRepository webappLoadingTimeRepository;

  private WebappPathRepository webappPathRepository;

  private RecorderService recorderService;


  RecorderController(SessionRepository sessionRepository,
                     WebappRepository webappRepository,
                     WebappLoadingTimeRepository webappLoadingTimeRepository,
                     WebappPathRepository webappPathRepository,
                     RecorderService recorderService) {
    this.sessionRepository = sessionRepository;
    this.webappRepository = webappRepository;
    this.webappLoadingTimeRepository = webappLoadingTimeRepository;
    this.webappPathRepository = webappPathRepository;
    this.recorderService = recorderService;
  }

  @PostMapping("/initializeSession")
  public void initializeSession(
          @CookieValue(value = USER_COOKIE_NAME, required = false) boolean isReturning,
          @CookieValue(value = SESSION_COOKIE_NAME, required = false) boolean isSession,
          HttpServletRequest request,
          HttpServletResponse response) {

    if (!isReturning && isSession) {
      log.error("Something went wrong: there is a session cookie, but no user cookie");
    }

    // Handle session
    if (!isSession) {
      Webapp webapp = getOrCreateWebapp(request.getServerName());
      sessionRepository.save(new Session(webapp, isReturning));
      log.info("Session added for " + request.getServerName());
    }

    // Manage cookies
    Cookie sessionCookie = (isSession) ? WebUtils.getCookie(request, SESSION_COOKIE_NAME) : new Cookie(SESSION_COOKIE_NAME, String.valueOf(true));
    sessionCookie.setMaxAge(SESSION_COOKIE_MAX_AGE_SEC);
    response.addCookie(sessionCookie);

    if (!isReturning) {
      Cookie userCookie = new Cookie(USER_COOKIE_NAME, String.valueOf(true));
      response.addCookie(userCookie);
    }
  }

  private Webapp getOrCreateWebapp(String serverName) {
    Optional<Webapp> webappOpt = webappRepository.findByUrl(serverName);
    return webappOpt.orElseGet(() -> webappRepository.save(new Webapp(serverName)));
  }

  @PostMapping("/pageLoadingPerformance")
  public void savePageLoadingPerformance(@RequestBody Map<String, Object> statistics) {
    Long loadingTime = ((Double) statistics.get("loadingTime")).longValue();
    WebappLoadingTime webappLoadingTime = new WebappLoadingTime(loadingTime);
    webappLoadingTimeRepository.save(webappLoadingTime);
  }

  @PostMapping("/urlPath")
  public void saveUrlPath(@RequestBody Map<String, String> pathParams) {
    String path = pathParams.get("path");
    WebappPath webappPath = webappPathRepository.findByPath(path).orElse(new WebappPath(path));
    recorderService.incrementWebappPathCounter(webappPath);
    webappPathRepository.save(webappPath);
  }

}
