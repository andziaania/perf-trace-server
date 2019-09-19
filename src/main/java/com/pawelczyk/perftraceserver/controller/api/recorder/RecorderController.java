package com.pawelczyk.perftraceserver.controller.api.recorder;

import com.pawelczyk.perftraceserver.model.SessionCount;
import com.pawelczyk.perftraceserver.model.Webapp;
import com.pawelczyk.perftraceserver.repository.SessionCountRepository;
import com.pawelczyk.perftraceserver.repository.WebappRepository;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

  private Logger log = LoggerFactory.getLogger(RecorderController.class);

  private SessionCountRepository sessionCountRepository;

  private WebappRepository webappRepository;

  RecorderController(SessionCountRepository sessionCountRepository, WebappRepository webappRepository) {
    this.sessionCountRepository = sessionCountRepository;
    this.webappRepository = webappRepository;
  }


  /**
   * -The web app must be in the DB
   * -If there is no APPLICATION_ID_COOKIE in the request, it is set in the response.
   * -If there is no SESSION_ID_COOKIE in the request, it is set in the response.
   */
  @GetMapping("/initializeSession444")
  public void initializeSession2() {
//    Cookie[] cookies = request.getCookies();
//    Optional<String> webappId = Arrays.stream(cookies)
//            .filter(cookie -> cookie.getName().equals(APPLICATION_ID_COOKIE_NAME))
//            .map(cookie -> cookie.getValue())
//            .findAny();
//    Optional<String> sessionId = Arrays.stream(cookies)
//            .filter(cookie -> cookie.getName().equals(SESSION_ID_COOKIE_NAME))
//            .map(cookie -> cookie.getValue())
//            .findAny();
//
//    UserOnWebapp userOnWebapp = userOnWebappRegister
//            .initializeSession(webappId, sessionId, request.getHeader("origin"), ajaxRequestUrl);
//
//    response.addCookie(new Cookie(APPLICATION_ID_COOKIE_NAME, userOnWebapp.getWebapp().getId().toString()));
//    response.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, String.valueOf(userOnWebapp.getId())));
  }

  @PostMapping("/initializeSession")
  public void initializeSession(
          @CookieValue(value = USER_COOKIE_NAME, required = false) boolean userId,
          @CookieValue(value = SESSION_COOKIE_NAME, required = false) boolean isSession,
          HttpServletRequest request,
          HttpServletResponse response) {

    final int SESSION_COOKIE_MAX_AGE_SEC = 60;

    if (!userId && isSession) {
      log.error("Something went wrong: there is a session cookie, but no user cookie");
    }

    Webapp webapp = getOrCreateWebapp(request.getServerName());

    if (!isSession) {
      incrementSessionCount(webapp, userId);
      log.info("Session cookie added for " + request.getServerName());
    }

    Cookie sessionCookie = (isSession) ? WebUtils.getCookie(request, SESSION_COOKIE_NAME) : new Cookie(SESSION_COOKIE_NAME, String.valueOf(true));
    sessionCookie.setMaxAge(SESSION_COOKIE_MAX_AGE_SEC);
    response.addCookie(sessionCookie);

    if (!userId) {
      Cookie userCookie = new Cookie(USER_COOKIE_NAME, String.valueOf(true));
      response.addCookie(userCookie);
    }
  }

  private Webapp getOrCreateWebapp(String serverName) {
    Optional<Webapp> webappOpt = webappRepository.findByUrl(serverName);
    return webappOpt.orElseGet(() -> createWebapp(serverName));
  }

  private void incrementSessionCount(Webapp webapp, boolean isReturningUser) {
    Optional<SessionCount> sessionCountOpt = sessionCountRepository.findByWebapp(webapp);
    long returningCount = isReturningUser ? 1L : 0L;
    SessionCount sessionCount = sessionCountOpt.isPresent() ?
            getIncreasedSessionCount(sessionCountOpt.get(), returningCount) : new SessionCount(webapp, 1L, returningCount);
    sessionCountRepository.save(sessionCount);
  }

  private SessionCount getIncreasedSessionCount(SessionCount sessionCount, long returningCount) {
    sessionCount.setCount(sessionCount.getCount() + 1);
    sessionCount.setReturningCount(sessionCount.getReturningCount() + returningCount);
    return sessionCount;
  }

  private Webapp createWebapp(String url) {
    Webapp webapp = new Webapp(url);
    return webappRepository.save(webapp);
  }
}
