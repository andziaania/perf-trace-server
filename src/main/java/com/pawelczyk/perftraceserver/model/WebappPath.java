package com.pawelczyk.perftraceserver.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author ania.pawelczyk
 * @since 04.10.2019.
 */
@Entity
public class WebappPath {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @UpdateTimestamp
  private LocalDateTime dateTime;

  @Column(unique = true)
  private String path;
  private Long counter;

  public WebappPath() { }

  public WebappPath(String path) {
    this.path = path;
    this.counter = 0L;
  }

  public Long getCounter() {
    return counter;
  }

  public void setCounter(Long counter) {
    this.counter = counter;
  }

}
