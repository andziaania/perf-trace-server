package com.pawelczyk.perftraceserver.model;

import com.pawelczyk.perftraceserver.converter.UsersNumberHourlyConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

  private LocalDate date;

  private Long usersNumber;

  private Long returningUsersNumber;

  @Convert(converter = UsersNumberHourlyConverter.class)
  private List<Long> usersNumberHourly;

  public WebappDaily() { }

  public WebappDaily(@NotNull LocalDate date, @NotNull List<Long> usersNumberHourly) {
    this.date = date;
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

  public LocalDate getDate() {
    return date;
  }

  public Long getUsersNumber() {
    return usersNumber;
  }

  public void setReturningUsersNumber(Long returningUsersNumber) {
    this.returningUsersNumber = returningUsersNumber;
  }
}
