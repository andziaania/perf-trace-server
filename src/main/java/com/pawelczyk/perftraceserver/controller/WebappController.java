package com.pawelczyk.perftraceserver.controller;

import com.pawelczyk.perftraceserver.controller.exception.WrongParameterException;
import com.pawelczyk.perftraceserver.model.Webapp;
import com.pawelczyk.perftraceserver.repository.WebappRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ania.pawelczyk
 * @since 06.09.2019.
 */
@RestController
public class WebappController {
  private WebappRepository webappRepository;

  public WebappController(WebappRepository webappRepository) {
    this.webappRepository = webappRepository;
  }

  @PostMapping("/webapps")
  @ResponseStatus(HttpStatus.CREATED)
  public void addWebapp(@RequestBody Webapp webapp) {
    if(webapp.getUrl() == null || webapp.getUrl().trim().isEmpty()) {
      throw new WrongParameterException("Passing incorrect argument: " + webapp +". Expecting: " + Webapp.class);
    }
    webappRepository.save(webapp);
  }

  @GetMapping("/webapps")
  public List<Webapp> getWebapps() {
    return webappRepository.findAll();
  }
}
