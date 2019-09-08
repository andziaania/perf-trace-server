package com.pawelczyk.perftraceserver.model;

import com.pawelczyk.perftraceserver.controller.WebappDailyController;
import com.pawelczyk.perftraceserver.converter.UsersNumberHourlyConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Arrays;
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

  private LocalDate date;

  private long usersNumber;

  @Convert(converter = UsersNumberHourlyConverter.class)
  private List<Long> usersNumberHourly;

  public List<Long> getUsersNumberHourly() {
    return usersNumberHourly;
  }

  // For the SQL performance issue, the hours data is kept in this entity
  private long h0;
  private long h1;
  private long h2;
  private long h3;
  private long h4;
  private long h5;
  private long h6;
  private long h7;
  private long h8;
  private long h9;
  private long h10;
  private long h11;
  private long h12;
  private long h13;
  private long h14;
  private long h15;
  private long h16;
  private long h17;
  private long h18;
  private long h19;
  private long h20;
  private long h21;
  private long h22;
  private long h23;

  public WebappDaily() {}

  public WebappDaily(LocalDate date, List<Long> usersNumberHourly) {
    this.date = date;
    this.usersNumberHourly = usersNumberHourly;
  }

//  public WebappDaily(LocalDate date, long[] hours) {
//    this.date = date;
//    if (hours.length != 24) throw new IllegalArgumentException();
//
//    this.h0 = hours[0]; h1 = hours[1];  h2 = hours[2];  h3 = hours[3];  h4 = hours[4];  h5 = hours[5];  h6 = hours[6];  h7 = hours[7];
//    h8 = hours[8];  h9 = hours[9];  h10 = hours[10];  h11 = hours[11];  h12 = hours[12];  h13 = hours[13];  h14 = hours[14];  h15 = hours[15];
//    h16 = hours[16];  h17 = hours[17];  h18 = hours[18];  h19 = hours[19];  h20 = hours[20];  h21 = hours[21];  h22 = hours[22];  h23 = hours[23];
//
//    Arrays.stream(hours).forEach(value -> this.usersNumber += value);
//  }
}
