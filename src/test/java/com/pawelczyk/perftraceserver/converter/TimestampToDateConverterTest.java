package com.pawelczyk.perftraceserver.converter;

import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import static org.junit.Assert.assertEquals;

/**
 * @author ania.pawelczyk
 * @since 12.09.2019.
 */
public class TimestampToDateConverterTest {

  private Long timestamp = 1568160000000L;
  private Date date = Date.valueOf("2019-09-11");

  @Test
  public void convertToDatabaseColumn() {
    Date testDate = new TimestampToDateConverter().convertToDatabaseColumn(timestamp);
    assertEquals(date, testDate);
  }

//  @Test
  public void convertToEntityAttribute() {
    System.out.println(date.toLocalDate());
    System.out.println(date.toLocalDate().atStartOfDay());
    System.out.println(Timestamp.valueOf(date.toLocalDate().atStartOfDay()));

    Long testTimestamp = new TimestampToDateConverter().convertToEntityAttribute(date);
    assertEquals(timestamp, testTimestamp);
  }


}