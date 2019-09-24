package com.pawelczyk.perftraceserver.controller;

import com.pawelczyk.perftraceserver.repository.WebappLoadingTimeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ania.pawelczyk
 * @since 24.09.2019.
 */
@RestController
@RequestMapping("/api/performance")
public class WebappLoadingTimeController {

  private WebappLoadingTimeRepository webappLoadingTimeRepository;

  WebappLoadingTimeController(WebappLoadingTimeRepository webappLoadingTimeRepository) {
    this.webappLoadingTimeRepository = webappLoadingTimeRepository;
  }

  @GetMapping("/loading")
  public Object getWabappLoadingTime() {
    return webappLoadingTimeRepository.findMaxLoadingTime();
  }
}
