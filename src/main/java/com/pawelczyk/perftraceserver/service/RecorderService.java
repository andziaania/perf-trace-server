package com.pawelczyk.perftraceserver.service;

import com.pawelczyk.perftraceserver.model.WebappPath;
import org.springframework.stereotype.Service;

/**
 * @author ania.pawelczyk
 * @since 30.10.2019.
 */
@Service
public class RecorderService {
  public void incrementWebappPathCounter(WebappPath webappPath) {
    webappPath.setCounter(webappPath.getCounter() + 1);
  }
}
