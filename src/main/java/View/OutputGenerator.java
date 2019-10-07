package View;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

public class OutputGenerator {

  public static String getOutputGenerator(Set<String> people) {
    ZonedDateTime melbourneTime = ZonedDateTime.now(ZoneId.of("Australia/Sydney"));
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MM yyyy");
    String melbourneDateFormatted = melbourneTime.format(dateFormat);
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
    String melbourneTimeFormatted = melbourneTime.format(timeFormat); //todo make new class for time

    Iterator<String> iterator = people.iterator();

    if (people.size() == 1) {
      return "Hello " + iterator.next() + " - the time on the server is " + melbourneTimeFormatted + " on "
          + melbourneDateFormatted;
    } else if (people.size() == 2) {
      return "Hello " + iterator.next() + " and " + iterator.next() + " - the time on the server is "
          + melbourneTimeFormatted + " on " + melbourneDateFormatted;
    } else {
      StringBuilder moreThanThreePeople = new StringBuilder();
      for (int i = 0; i < people.size() - 1; i++) {
        moreThanThreePeople.append(iterator.next());
        moreThanThreePeople.append(", ");
      }
      return "Hello " + moreThanThreePeople + "and " + iterator.next() + " - the time on the server is "
          + melbourneTimeFormatted + " on " + melbourneDateFormatted;
    }
  }

  public static String putOutput() {
    return "Person has been successfully changed!";
  }

  public static String postOutput() {
    return "Person has been successfully added!";
  }

  public static String deleteOutput() {
    return "Person has been successfully deleted!";
  }

  public static String putErrorOutput() {
    return "Error, the name you are attempting to change too is invalid!";
  }

  public static String postErrorOutput() {
    return "Error, the name you are attempting to add is invalid!";
  }

  public static String deleteErrorOutput() {
    return "Error, the name you are attempting to delete is invalid!";
  }
}
