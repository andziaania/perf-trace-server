package com.pawelczyk.perftraceserver.model;

import com.pawelczyk.perftraceserver.converter.TimestampToDateConverter;
import com.pawelczyk.perftraceserver.converter.UsersNumberHourlyConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ania.pawelczyk
 * @since 08.09.2019.
 */
@Entity
public class WebappDaily {
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Webapp webapp;

  @Convert(converter = TimestampToDateConverter.class)
  private Long date;

  private Long timestamp;

  private Long usersNumber;

  private Long returningUsersNumber;

  @Convert(converter = UsersNumberHourlyConverter.class)
  private List<Long> usersNumberHourly;

  public WebappDaily() { }

  public WebappDaily(Long timestamp, @NotNull List<Long> usersNumberHourly) {
    this.timestamp = timestamp;
    this.date = timestamp;
    this.usersNumberHourly = usersNumberHourly;
    setUsersNumber(usersNumberHourly);
  }

  // Sums the hours
  public void setUsersNumber(@NotNull List<Long> usersNumberHourly) {
    if (usersNumberHourly == null) throw new IllegalArgumentException("usersNumberHourly parameter cannot be null");
    this.usersNumber = (usersNumberHourly.size() != 0) ?
            usersNumberHourly.stream().reduce((a, b) -> a + b).get()
            : 0L;
  }

  public List<Long> getUsersNumberHourly() {
    return usersNumberHourly;
  }

  public Long getUsersNumber() {
    return usersNumber;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  @Convert(converter = UsersNumberHourlyConverter.class)
  public void setUsersNumberHourly(List<Long> usersNumberHourly) {
    this.usersNumberHourly = usersNumberHourly;
  }

  public void setReturningUsersNumber(Long returningUsersNumber) {
    this.returningUsersNumber = returningUsersNumber;
  }
}
