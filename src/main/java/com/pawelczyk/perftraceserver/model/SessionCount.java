package com.pawelczyk.perftraceserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author ania.pawelczyk
 * @since 18.09.2019.
 */
@Entity
public class SessionCount {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  Webapp webapp;

  Long count;

  public SessionCount() { }

  public SessionCount(Webapp webapp, Long count) {
    this.webapp = webapp;
    this.count = count;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }
}
