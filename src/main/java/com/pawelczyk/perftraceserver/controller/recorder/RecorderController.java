package com.pawelczyk.perftraceserver.controller.recorder;

import com.pawelczyk.perftraceserver.model.SessionCount;
import com.pawelczyk.perftraceserver.model.Webapp;
import com.pawelczyk.perftraceserver.repository.SessionCountRepository;
import com.pawelczyk.perftraceserver.repository.WebappRepository;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author ania.pawelczyk
 * @since 17.09.2019.
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/recorder")
public class RecorderController {

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
  @GetMapping("/initializeSession")
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
          @RequestParam("webappId") Long webappId,
          @CookieValue(value = "pt_userid", defaultValue = "0") Long userId,
          @CookieValue(value = "pt_sessionid", defaultValue = "0") Long sessionId,
          HttpServletRequest request,
          HttpServletResponse response) {

    Optional<Webapp> webappOpt = webappRepository.findById(webappId);
    Webapp webapp = webappOpt.orElseGet(() -> createWebapp(request.getServerName()));

    Optional<SessionCount> sessionCountOpt = sessionCountRepository.findByWebapp(webapp);
    SessionCount sessionCount = sessionCountOpt.isPresent() ?
            getIncreasedSessionCount(sessionCountOpt.get()) : new SessionCount(webapp, 1L);
    sessionCountRepository.save(sessionCount);
  }

  private SessionCount getIncreasedSessionCount(SessionCount sessionCount) {
    sessionCount.setCount(sessionCount.getCount() + 1);
    return sessionCount;
  }

  private Webapp createWebapp(String url) {
    Webapp webapp = new Webapp(url);
    return webappRepository.save(webapp);
  }
}
