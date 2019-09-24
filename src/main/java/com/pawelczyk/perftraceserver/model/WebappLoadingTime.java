package com.pawelczyk.perftraceserver.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author ania.pawelczyk
 * @since 24.09.2019.
 */
@Entity
public class WebappLoadingTime {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @UpdateTimestamp
  private LocalDateTime dateTime;

  private Long loadingTime;

  public WebappLoadingTime() {}

  public WebappLoadingTime(Long loadingTime) {
    this.loadingTime = loadingTime;
  }
}
