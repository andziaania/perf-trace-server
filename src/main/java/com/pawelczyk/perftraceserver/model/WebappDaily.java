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

  @Convert(converter = UsersNumberHourlyConverter.class)
  private List<Long> usersNumberHourly;

  private Long returningUsersNumber;

  @Convert(converter = UsersNumberHourlyConverter.class)
  private List<Long> returningUsersNumberHourly;

  public WebappDaily() { }

  public WebappDaily(@NotNull LocalDate date, @NotNull List<Long> usersNumberHourly, @NotNull List<Long> returningUsersNumberHourly) {
    this.date = date;
    this.usersNumberHourly = usersNumberHourly;
    setUsersNumber(usersNumberHourly);
    this.returningUsersNumberHourly = returningUsersNumberHourly;
    setReturningUsersNumber(returningUsersNumberHourly);
  }

  // Sums the hours
  public void setUsersNumber(@NotNull List<Long> usersNumberHourly) {
    if (usersNumberHourly == null) throw new IllegalArgumentException("usersNumberHourly parameter cannot be null");
    this.usersNumber = getSumOfElements(usersNumberHourly);
  }

  public List<Long> getUsersNumberHourly() {
    return usersNumberHourly;
  }

  // Sums the hours
  public void setReturningUsersNumber(@NotNull List<Long> returningUsersNumberHourly) {
    if (returningUsersNumberHourly == null) throw new IllegalArgumentException("usersNumberHourly parameter cannot be null");
    this.returningUsersNumber = getSumOfElements(returningUsersNumberHourly);
  }

  private Long getSumOfElements(@NotNull List<Long> elements) {
    return (elements.size() != 0) ?
            elements.stream().reduce((a, b) -> a + b).get()
            : 0L;
  }

  public List<Long> getReturningUsersNumberHourly() {
    return returningUsersNumberHourly;
  }

  public LocalDate getDate() {
    return date;
  }

  public Long getUsersNumber() {
    return usersNumber;
  }

  public Long getReturningUsersNumber() {
    return returningUsersNumber;
  }
}
