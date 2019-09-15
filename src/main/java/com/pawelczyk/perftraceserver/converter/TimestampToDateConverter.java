package com.pawelczyk.perftraceserver.converter;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author ania.pawelczyk
 * @since 10.09.2019.
 */
public class TimestampToDateConverter implements AttributeConverter<Long, Date> {
  @Override
  public Date convertToDatabaseColumn(Long timestampLong) {
    Timestamp timestamp = new Timestamp(timestampLong);
    return Date.valueOf(timestamp.toLocalDateTime().toLocalDate());
  }

  @Override
  public Long convertToEntityAttribute(Date date) {
    return Timestamp.valueOf(date.toLocalDate().atStartOfDay()).getTime();
  }
}
