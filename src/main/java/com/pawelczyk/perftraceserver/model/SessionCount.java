package com.pawelczyk.perftraceserver.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch=FetchType.LAZY)
  private Webapp webapp;

  private Long count;

  /**
   * informs how many of the count are returning users
   */
  private Long returningCount;

  public SessionCount() { }

  public SessionCount(Webapp webapp, Long count, Long returningCount) {
    this.webapp = webapp;
    this.count = count;
    this.returningCount = returningCount;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public Long getReturningCount() {
    return returningCount;
  }

  public void setReturningCount(Long returningCount) {
    this.returningCount = returningCount;
  }
}
