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
      throw new IllegalArgumentException("Array of hours must be of length 24, but is " + ((hours == null) ? "null" : hours.size() ));
    }

    List<String> usersNumberHourly = hours.stream()
            .map(String::valueOf)
            .collect(Collectors.toList());
    return String.join(DELIMITER, usersNumberHourly);




//    // input primitive integer array
//    int[] intArray = { 1, 2, 3, 4, 5 };
//
//    String strArray[] = Arrays.stream(hours.toArray())
//            .mapToObj(String::valueOf)
//            .toArray(String[]::new);
//
//    System.out.println(Arrays.toString(strArray));
//
//    Arrays.stream(hours.toArray()).map
//
//    String usersNumberHourly = Arrays.stream(hours.toArray()).map(val -> val.toString()).reduce((a, b) -> a + DELIMITER + b, String::concat);
//    return usersNumberHourly;
  }

  @Override
  public  List<Long> convertToEntityAttribute(String usersNumberHourly) {
    String[] hours = usersNumberHourly.split(DELIMITER);
    return Arrays.stream(hours).map(val -> Long.parseLong(val)).collect(Collectors.toList());
  }
}
