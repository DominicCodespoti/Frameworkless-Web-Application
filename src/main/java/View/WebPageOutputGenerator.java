package View;

import static Utilities.Constants.MELBOURNE_TIMEZONE;

import Utilities.DateTimeGenerator;
import java.util.Iterator;
import java.util.Set;

public class WebPageOutputGenerator implements OutputGenerator {

  @Override
  public String getOutputFormatter(String names, String[] formattedDateTime) {
    return "Hello " + names + " - the time on the server is " + formattedDateTime[0] + " on " + formattedDateTime[1];
  }

  @Override
  public String getOutputGenerator(Set<String> people) {
    Iterator<String> iterator = people.iterator();
    String[] dateAndTime = DateTimeGenerator.getDateAndTime(MELBOURNE_TIMEZONE);

    switch (people.size()) {
      case 1:
        return getOutputFormatter(iterator.next(), dateAndTime);
      case 2:
        return getOutputFormatter(iterator.next() + " and " + iterator.next(), dateAndTime);
      default:
        StringBuilder moreThanThreePeople = new StringBuilder();
        for (int i = 0; i < people.size() - 1; i++) {
          moreThanThreePeople.append(iterator.next());
          moreThanThreePeople.append(", ");
        }
        moreThanThreePeople.append("and ").append(iterator.next());
        return getOutputFormatter(moreThanThreePeople.toString(), dateAndTime);
    }
  }

  @Override
  public String putOutput() {
    return "Person has been successfully changed!";
  }

  @Override
  public String postOutput() {
    return "Person has been successfully added!";
  }

  @Override
  public String deleteOutput() {
    return "Person has been successfully deleted!";
  }

  @Override
  public String putErrorOutput() {
    return "Error, the name you are attempting to change too is invalid!";
  }

  @Override
  public String postErrorOutput() {
    return "Error, the name you are attempting to add is invalid!";
  }

  @Override
  public String deleteErrorOutput() {
    return "Error, the name you are attempting to delete is invalid!";
  }
}
