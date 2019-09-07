package com.pawelczyk.perftraceserver.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author ania.pawelczyk
 * @since 06.09.2019.
 */
@Entity
public class Webapp {
  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String url;

  @UpdateTimestamp
  private LocalDateTime timestamp;

  public Webapp(){}

  public Webapp(String url) {
    this.url = url;
  }

  public Long getId() {
    return id;
  }

  @Override
  public boolean equals(Object other) {
    if (!this.getClass().equals(other.getClass())) return false;
    Webapp otherWebapp = (Webapp) other;
    return this.url.equals(otherWebapp.url)
            && this.id.equals(otherWebapp.id);
  }

  public String getUrl() {
    return url;
  }

  @ Override
  public String toString() {
    return String.format("Webapp Url: %s, Id: %d", url, id);
  }
}
