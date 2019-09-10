package com.pawelczyk.perftraceserver.model;

import com.pawelczyk.perftraceserver.converter.TimestampToDateConverter;
import com.pawelczyk.perftraceserver.converter.UsersNumberHourlyConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
  @GeneratedValue
  private long id;

  @ManyToOne
  private Webapp webapp;

  @Convert(converter = TimestampToDateConverter.class)
  private Long timestamp;

  private long usersNumber;

  @Convert(converter = UsersNumberHourlyConverter.class)
  private List<Long> usersNumberHourly;

  public WebappDaily() { }

  public WebappDaily(Long timestamp, @NotNull List<Long> usersNumberHourly) {
    this.timestamp = timestamp;
    this.usersNumberHourly = usersNumberHourly;
    setUsersNumber(usersNumberHourly);
  }

  private void setUsersNumber(@NotNull List<Long> usersNumberHourly) {
    if (usersNumberHourly == null) throw new IllegalArgumentException("usersNumberHourly parameter cannot be null");
    this.usersNumber = (usersNumberHourly.size() != 0) ?
            usersNumberHourly.stream().reduce((a, b) -> a + b).get()
            : 0;
  }

  public List<Long> getUsersNumberHourly() {
    return usersNumberHourly;
  }

  public long getUsersNumber() {
    return usersNumber;
  }
}
