package com.pawelczyk.perftraceserver.controller.getter;

import com.pawelczyk.perftraceserver.model.WebappPath;
import com.pawelczyk.perftraceserver.repository.WebappPathRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ania.pawelczyk
 * @since 04.10.2019.
 */
@RestController
@RequestMapping("/api/paths")
public class WebappPathController {

  private WebappPathRepository webappPathRepository;

  WebappPathController(WebappPathRepository webappPathRepository) {
    this.webappPathRepository = webappPathRepository;
  }

  @GetMapping(value = "/ordered", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<WebappPath> getWebappPathsOrdered() {
    return webappPathRepository.findByOrderByCounterDesc();
  }

}
