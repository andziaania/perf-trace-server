package com.pawelczyk.perftraceserver.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

/**
 * @author ania.pawelczyk
 * @since 18.09.2019.
 */
@Entity
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch=FetchType.LAZY)
  private Webapp webapp;

  @UpdateTimestamp
  private LocalDateTime timestamp;

  private boolean isReturning;

  public Session() { }

  public Session(Webapp webapp, boolean isReturning) {
    this.webapp = webapp;
    this.isReturning = isReturning;
  }

  public boolean getIsReturning() {
    return isReturning;
  }

  public void setIsReturning(boolean isReturning) {
    this.isReturning = isReturning;
  }

  public Webapp getWebapp() {
    return webapp;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }
}
