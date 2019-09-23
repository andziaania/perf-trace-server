package com.pawelczyk.perftraceserver.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ania.pawelczyk
 * @since 08.09.2019.
 */

@Converter
public class UsersNumberHourlyConverter implements AttributeConverter< List<Long>, String> {

  private final String DELIMITER = ";";

  @Override
  public String convertToDatabaseColumn( List<Long> hours) throws IllegalArgumentException {
    if (hours == null || hours.size() != 24) {
      throw new IllegalArgumentException("Array of hours must be of length 24, but is " + ((hours == null) ? "null" : hours.size()));
    }

    List<String> usersNumberHourly = hours.stream()
            .map(String::valueOf)
            .collect(Collectors.toList());
    return String.join(DELIMITER, usersNumberHourly);
  }

  @Override
  public  List<Long> convertToEntityAttribute(String usersNumberHourly) {
    String[] hours = usersNumberHourly.split(DELIMITER);
    return Arrays.stream(hours).map(Long::parseLong).collect(Collectors.toList());
  }
}
