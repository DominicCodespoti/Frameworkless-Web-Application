package Utilities;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeGenerator {

  public static String[] getDateAndTime(String tzDatabaseName){
    ZonedDateTime melbourneTime = ZonedDateTime.now(ZoneId.of(tzDatabaseName));
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MM yyyy");
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
    return new String[]{melbourneTime.format(dateFormat), melbourneTime.format(timeFormat)};
  }
}
